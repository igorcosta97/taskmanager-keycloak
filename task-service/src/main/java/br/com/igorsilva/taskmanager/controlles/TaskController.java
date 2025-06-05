package br.com.igorsilva.taskmanager.controlles;

import br.com.igorsilva.taskmanager.dtos.TaskDto;
import br.com.igorsilva.taskmanager.entities.TaskModel;
import br.com.igorsilva.taskmanager.services.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);


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