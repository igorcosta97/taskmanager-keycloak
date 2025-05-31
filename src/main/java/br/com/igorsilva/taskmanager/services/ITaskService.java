package br.com.igorsilva.taskmanager.services;

import br.com.igorsilva.taskmanager.entities.TaskModel;

import java.util.List;
import java.util.UUID;

public interface ITaskService {
    List<TaskModel> getTasksByUserId(String userId);
    List<TaskModel> getAllTasks();
    TaskModel createTask(TaskModel taskModel);
    TaskModel getTaskById(String taskId);
    TaskModel updateTask(String taskId, TaskModel taskModel);
    void deleteTaskById(String taskId);
    void deleteAllTasksByUserId(String userId);


}
