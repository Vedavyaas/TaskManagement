package com.amdox.taskmanagement.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t FROM TaskEntity t WHERE t.assignedUser = :user AND t.createdBy = :admin")
    List<TaskEntity> findTaskEntityByAssignedUserAndCreatedBy(UserEntity user, UserEntity admin);

    boolean existsByTitle(String title);

    @Query("SELECT t FROM TaskEntity t WHERE t.assignedUser = :userEntity")
    List<TaskEntity> findTaskEntityByAssignedUser(UserEntity userEntity);

    @Modifying
    @Transactional
    @Query("DELETE FROM TaskEntity t WHERE t.title = :title AND t.createdBy = :createdBy")
    void removeTaskEntitiesByTitleAndCreatedBy(String title, UserEntity createdBy);

    @Modifying
    @Transactional
    @Query("UPDATE TaskEntity t SET t.description =: description, t.updatedAt = :now WHERE t.title = :title AND t.assignedUser = :user")
    void updateDescriptionAndUpdatedAtByTitleAndUser(String description, LocalDateTime now, String title, UserEntity user);

    @Query("SELECT COUNT(t) > 0 FROM TaskEntity t WHERE t.title = :title AND t.assignedUser = :userEntity")
    boolean existsByTitleAndUser(String title, UserEntity userEntity);

    @Modifying
    @Transactional
    @Query("UPDATE TaskEntity t SET t.status = :status, t.completedAt = :now WHERE t.title = :title AND  t.assignedUser = :userEntity")
    void updateStatusAndCompletedAtByTitleAndUser(String status, LocalDateTime now, String title, UserEntity userEntity);

    @Modifying
    @Transactional
    @Query("UPDATE TaskEntity t SET t.dueDate = :dueDate, t.updatedAt = :now WHERE t.title = :title AND t.assignedUser = :userEntity")
    void updateDueDateAndUpdatedAtByTitleAndUser(LocalDateTime dueDate, LocalDateTime now, String title, UserEntity userEntity);

    @Modifying
    @Transactional
    @Query("UPDATE TaskEntity t SET t.priority = :priority, t.updatedAt = :now WHERE t.title = :title AND t.assignedUser = :userEntity")
    void updatePriorityAndUpdatedAtByTitleAndUser(String priority, LocalDateTime now, String title, UserEntity userEntity);
}
