package br.com.igorsilva.taskmanager.controlles;

import br.com.igorsilva.taskmanager.dtos.EmailRequestDTO;
import br.com.igorsilva.taskmanager.dtos.TaskDto;
import br.com.igorsilva.taskmanager.entities.TaskModel;
import br.com.igorsilva.taskmanager.services.ITaskService;
import br.com.igorsilva.taskmanager.services.RabbitProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final RestTemplate restTemplate = new RestTemplate();

    private final RabbitProducer rabbitProducer;
    public TaskController(RabbitProducer rabbitProducer) {
        this.rabbitProducer = rabbitProducer;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskModel>> getAllTasks(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Requisição recebida para obter todas as tarefas do usuário: {}", userId);
        var allTasks = taskService.getAllTasks();
        return ResponseEntity.status(HttpStatus.FOUND).body(allTasks);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TaskModel>> getTasksFromUserId(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Requisição recebida para obter as tarefas do usuário: {}", userId);
        var listTasksUser = taskService.getTasksByUserId(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(listTasksUser);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Requisição recebida para criar uma nova tarefa para o usuário: {}", userId);
        TaskModel newTask = new TaskModel();
        BeanUtils.copyProperties(taskDto, newTask);
        newTask.setUserId(userId);
        TaskModel taskCreated = taskService.createTask(newTask);
        log.info("Tarefa criada com sucesso: {}", taskCreated);

        // Preparar mensagem para RabbitMQ
        String mensagem = "Nova tarefa criada:" + taskCreated.getTitle();
        // Enviar notificação via RabbitMQ
        rabbitProducer.sendNotification(mensagem);
        /*
        // Enviar notificação de e-mail após a criação da tarefa
        String emailServiceUrl = "http://localhost:8084/email/send";
        EmailRequestDTO emailRequest = new EmailRequestDTO(
                jwt.getClaimAsString("email"),
                taskCreated.getTitle(),
                taskCreated.getDescription()
        );

        // Criar headers e adicionar Authorization com Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt.getTokenValue());  // adiciona o token JWT no header Authorization
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Criar HttpEntity com body e headers
        HttpEntity<EmailRequestDTO> requestEntity = new HttpEntity<>(emailRequest, headers);

        // Fazer a chamada POST passando o HttpEntity
        ResponseEntity<Void> emailResponse = restTemplate.postForEntity(emailServiceUrl, requestEntity, Void.class);

        if (emailResponse.getStatusCode() != HttpStatus.OK) {
            log.error("Erro ao enviar notificação de e-mail para: {}", emailRequest.recipient());
        }else{
            log.info("Notificação de e-mail enviada com sucesso para: {}", emailRequest.recipient());
        }
        */

        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskModel> updateTask(@PathVariable String id, @RequestBody TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Requisição recebida para atualizar a tarefa com ID: {} para o usuário: {}", id, userId);
        TaskModel existingTask = taskService.getTaskById(id);

        if (!existingTask.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        TaskModel updatedTask = new TaskModel();
        BeanUtils.copyProperties(taskDto, updatedTask);
        updatedTask.setId(id);
        updatedTask.setUserId(userId);

        TaskModel task = taskService.updateTask(id, updatedTask);
        log.info("Tarefa atualizada com sucesso: {}", task);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTaskById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Requisição recebida para deletar a tarefa com ID: {} do usuário: {}", id, userId);
        TaskModel task = taskService.getTaskById(id);

        if (!task.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        taskService.deleteTaskById(id);
        log.info("Tarefa com ID: {} deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteAllTasksByUserId(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Requisição recebida para deletar todas as tarefas do usuário: {}", userId);
        taskService.deleteAllTasksByUserId(userId);
        log.info("Todas as tarefas do usuário com ID: {} foram deletadas com sucesso", userId);
        return ResponseEntity.noContent().build();
    }
}