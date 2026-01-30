package com.amdox.taskmanagement.Entity;

import com.amdox.taskmanagement.Enum.SprintState;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="sprints")
public class Sprint {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String sprintName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private SprintState state;

    private Long projectId;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Sprint() {
    }

    public Sprint(String sprintName, LocalDateTime startDate, LocalDateTime endDate, SprintState state, Long projectId) {
        this.sprintName = sprintName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.projectId = projectId;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public SprintState getState() {
        return state;
    }

    public void setState(SprintState state) {
        this.state = state;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
