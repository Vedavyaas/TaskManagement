package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Service.NotificationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/post/notification")
    public String postNotification(@RequestParam String to, @RequestParam String message) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return notificationService.postMessage(user, to, message);
    }
}
