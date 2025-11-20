package com.amdox.taskmanagement.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t.createdAt FROM TaskEntity t WHERE t.assignedUser = :user AND t.title = :title")
    LocalDateTime findCreatedAtByUserAndTitle(UserEntity user, String title);

    @Query("SELECT t FROM TaskEntity t WHERE t.title = :title AND t.createdBy = :s")
    Optional<TaskEntity> getTaskEntityByTitleAndUser(String title, UserEntity s);

    @Query("SELECT t FROM TaskEntity t WHERE t.assignedUser = :user AND t.createdBy = :admin")
    List<TaskEntity> findTaskEntityByAssignedUserAndCreatedBy(UserEntity user, UserEntity admin);

    boolean existsByTitle(String title);

    @Modifying
    @Transactional
    @Query("DELETE FROM TaskEntity t WHERE t.title = :title AND t.assignedUser = :user AND t.createdBy = :admin")
    void removeTaskEntitiesByTitleAndAssignedUserAndCreatedBy(String title, UserEntity user, UserEntity admin);
}
