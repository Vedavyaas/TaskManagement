package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Entity.Notification;
import com.amdox.taskmanagement.Entity.NotificationScheme;
import com.amdox.taskmanagement.Enum.NotificationEvent;
import com.amdox.taskmanagement.Repository.NotificationRepository;
import com.amdox.taskmanagement.Repository.NotificationSchemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NotificationService {

    @Autowired
    private MailSenderService emailService; // Using existing MailSenderService

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private NotificationSchemeRepository notificationSchemeRepo;

    public void notify(Long projectId,
                       NotificationEvent eventType,
                       Set<String> emails,
                       String subject,
                       String message,
                       Long entityId) {

        Set<String> finalRecipients = notificationSchemeRepo.findByProjectIdAndEventType(projectId, eventType)
                .map(scheme -> resolveRecipient(scheme, emails)).orElse(emails);

        for (String email : finalRecipients) {
            emailService.sendMail(email, subject, message); // Use sendMail instead of send

            Notification notification = new Notification();
            notification.setRecipientEmail(email);
            notification.setSubject(subject);
            notification.setBody(message);
            notification.setEvent(eventType);
            notification.setEntityId(entityId);
            notification.setCreatedAt(java.time.LocalDateTime.now()); // Ensure timestamp is set

            notificationRepo.save(notification);
        }
    }

    private Set<String> resolveRecipient(NotificationScheme scheme, Set<String> fallBack) {
        return scheme.getReceipient();
    }
}
