package br.com.igorsilva.taskmanager.controlles;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String getTasks() {
        return "List of TAsk";
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createTask() {
        return "Task created";
    }
}
