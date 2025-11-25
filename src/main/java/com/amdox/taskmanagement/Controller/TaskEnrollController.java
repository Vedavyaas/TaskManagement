package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Assests.TaskDTO;
import com.amdox.taskmanagement.Assests.TaskEnrollment;
import com.amdox.taskmanagement.Service.TaskEnrollService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TaskEnrollController {
    private final TaskEnrollService taskEnrollService;

    public TaskEnrollController(TaskEnrollService taskEnrollService) {
        this.taskEnrollService = taskEnrollService;
    }

    @GetMapping("/task/getAll")
    public List<TaskDTO> getAll(@RequestParam String username) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.getAllTasks(admin, username);
    }

    @GetMapping("/task/getSelf")
    public List<TaskDTO> getSelf() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.getAllTasks(user);
    }

    @PostMapping("/task/create")
    public String createTask(@RequestBody TaskEnrollment taskEnrollment) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.createTask(taskEnrollment, admin);
    }

    @DeleteMapping("/task/delete")
    public String deleteTask(@RequestParam String title) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.deleteTask(title, admin);
    }

    @PutMapping("/task/update/description")
    public String updateTaskDescription(@RequestParam String title, @RequestParam String description) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updateTaskDescription(title, description, user);
    }

    @PutMapping("/task/update/description/other")
    public String updateTaskDescription(@RequestParam String title, @RequestParam String description, @RequestParam String user) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updateTaskDescription(title, description, user, admin);
    }

    @PutMapping("/task/update/status")
    public String updateTaskStatus(@RequestParam String title, @RequestParam String status) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updateTaskStatus(title, status, user);
    }

    @PutMapping("/task/update/status/other")
    public String updateTaskStatus(@RequestParam String title, @RequestParam String status, @RequestParam String user) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updateTaskStatus(title, status, user, admin);
    }

    @PutMapping("/task/update/dueDate")
    public String updateDueDate(@RequestParam String title, @RequestParam LocalDateTime dueDate) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updateDueDate(dueDate, title, user);
    }

    @PutMapping("/task/update/dueDate/other")
    public String updateDueDate(@RequestParam String title, @RequestParam LocalDateTime dueDate, @RequestParam String user) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updateDueDate(dueDate, title, user, admin);
    }

    @PutMapping("/task/update/priority")
    public String updatePriority(@RequestParam String title, @RequestParam String priority) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updatePriority(title, priority, user);
    }

    @PutMapping("/task/update/priority/other")
    public String updatePriority(@RequestParam String title, @RequestParam String priority, @RequestParam String user) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskEnrollService.updatePriority(title, priority, user, admin);
    }
}