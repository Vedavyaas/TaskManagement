package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Assests.Priority;
import com.amdox.taskmanagement.Assests.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private UserEntity assignedUser;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private UserEntity createdBy;
    @OneToOne
    private WorkFlowEntity workFlow;

    public TaskEntity() {
    }

    public TaskEntity(String title, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime completedAt, LocalDateTime dueDate, Priority priority, UserEntity assignedUser, UserEntity createdBy) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.dueDate = dueDate;
        this.priority = priority;
        this.assignedUser = assignedUser;
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public UserEntity getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(UserEntity assignedUser) {
        this.assignedUser = assignedUser;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public WorkFlowEntity getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlowEntity workFlow) {
        this.workFlow = workFlow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }//getters and setters
}
