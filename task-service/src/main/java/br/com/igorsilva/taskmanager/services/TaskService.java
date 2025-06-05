package br.com.igorsilva.taskmanager.services;

import br.com.igorsilva.taskmanager.entities.TaskModel;
import br.com.igorsilva.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public List<TaskModel> getTasksByUserId(String userId) {
       var userTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getUserId().equals(userId))
                .toList();

        return userTasks;
    }

    @Override
    public List<TaskModel> getAllTasks() {
        var allTasks = taskRepository.findAll();
        return allTasks;
    }

    @Override
    public TaskModel createTask(TaskModel taskModel) {
        return taskRepository.save(taskModel);
    }

    @Override
    public TaskModel getTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }

    @Override
    public TaskModel updateTask(String taskId, TaskModel taskModel) {
        TaskModel existingTask = getTaskById(taskId);
        existingTask.setTitle(taskModel.getTitle());
        existingTask.setDescription(taskModel.getDescription());
        existingTask.setStatus(taskModel.getStatus());
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTaskById(String taskId) {
        TaskModel taskToDelete = getTaskById(taskId);
        taskRepository.delete(taskToDelete);
    }

    @Override
    public void deleteAllTasksByUserId(String userId) {
        List<TaskModel> userTasks = getTasksByUserId(userId);
        if (userTasks.isEmpty()) {
            throw new RuntimeException("No tasks found for user with id: " + userId);
        }
        taskRepository.deleteAll(userTasks);
    }

}
