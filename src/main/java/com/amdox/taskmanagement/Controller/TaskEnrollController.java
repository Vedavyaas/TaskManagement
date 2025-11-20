package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Assests.TaskDisplayer;
import com.amdox.taskmanagement.Assests.TaskEnrollment;
import com.amdox.taskmanagement.Service.TaskEnrollService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskEnrollController {
    private final TaskEnrollService taskEnrollService;

    public TaskEnrollController(TaskEnrollService taskEnrollService) {
        this.taskEnrollService = taskEnrollService;
    }

    @GetMapping("/task/getAll")
    public List<TaskDisplayer> getAll(@RequestParam String email, @RequestParam String password, @RequestParam String userEmail) {
        return taskEnrollService.getAllTasks(email, password, userEmail);
    }

    @PostMapping("/task/create")
    public String createTask(@RequestBody TaskEnrollment taskEnrollment) {
        return taskEnrollService.createTask(taskEnrollment);
    }

    @PutMapping("/task/modify")
    public String modifyTask(@RequestBody TaskEnrollment taskEnrollment) {
        return taskEnrollService.modifyTask(taskEnrollment);
    }

    @DeleteMapping("/task/delete")
    public String deleteTask(@RequestBody TaskEnrollment taskEnrollment) {
        return taskEnrollService.deleteTask(taskEnrollment);
    }

    @PutMapping("/task/completed")
    public String completedTask(@RequestBody TaskEnrollment taskEnrollment) {
        return taskEnrollService.setTaskCompleted(taskEnrollment);
    }
}