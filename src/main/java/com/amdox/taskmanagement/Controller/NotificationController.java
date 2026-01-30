package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Enum.NotificationEvent;
import com.amdox.taskmanagement.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notify")
    public ResponseEntity<String> sendNotification(@RequestParam Long projectId,
                                                   @RequestParam NotificationEvent eventType,
                                                   @RequestParam Long entityId,
                                                   @RequestParam Set<String> emails,
                                                   @RequestParam String subject,
                                                   @RequestParam String message) {

        notificationService.notify(projectId, eventType, emails, subject, message, entityId);

        return ResponseEntity.ok("Notification sent successful");
    }
}
