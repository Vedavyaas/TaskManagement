package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Entity.NotificationScheme;
import com.amdox.taskmanagement.Enum.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationSchemeRepository extends JpaRepository<NotificationScheme, Long> {
    Optional<NotificationScheme> findByProjectIdAndEventType(Long projectId, NotificationEvent eventType);
}
