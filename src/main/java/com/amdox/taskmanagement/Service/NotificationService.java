package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Repository.NotificationEntity;
import com.amdox.taskmanagement.Repository.NotificationRepository;
import com.amdox.taskmanagement.Repository.UserEntity;
import com.amdox.taskmanagement.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
     private final NotificationRepository notificationRepository;
     private final UserRepository userRepository;
    private final JavaMailSenderImpl mailSender;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, JavaMailSenderImpl mailSender) {
        this.notificationRepository = notificationRepository;
         this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public String postMessage(String user, String to, String message) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        Optional<UserEntity> toEntity = userRepository.findByUsername(to);

        if(userEntity.isPresent() && toEntity.isPresent()){
            if (userEntity.get().getRole().equals("ADMIN")) {
                notificationRepository.save(new NotificationEntity(toEntity.get().getEmail(), message));
                return "Notification posted successfully";
            }
            return "You dont have enough permissions";
        }
        return "User not found";
    }

    @Scheduled(fixedRate = 600_000)
    @Transactional
    public void fetchNotification() {
        Page<NotificationEntity> page = notificationRepository.findAll(PageRequest.of(0, 10));
        List<NotificationEntity> notifications = page.getContent();

        if (notifications.isEmpty()) {
            return;
        }

        for (NotificationEntity entity : notifications) {
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setSubject("Notification posted");
                mailMessage.setTo(entity.getEmail());
                mailMessage.setText("Notification received from Amdox Tech");

                mailSender.send(mailMessage);
                notificationRepository.delete(entity);

            } catch (Exception e) {
                // 4. Log the error but don't stop the whole loop
                //log.error("Failed to send email to: " + entity.getEmail(), e);
            }
        }
    }
}
