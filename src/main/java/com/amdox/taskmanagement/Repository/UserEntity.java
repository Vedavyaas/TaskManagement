package com.amdox.taskmanagement.Repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String role;
    private String fullName;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "assignedUser")
    private List<TaskEntity> tasksAssigned;
    @OneToMany(mappedBy = "createdBy")
    private List<TaskEntity> tasksCreated;

    public UserEntity() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TaskEntity> getTasksAssigned() {
        return tasksAssigned;
    }

    public void setTasksAssigned(List<TaskEntity> tasksAssigned) {
        this.tasksAssigned = tasksAssigned;
    }

    public List<TaskEntity> getTasksCreated() {
        return tasksCreated;
    }

    public void setTasksCreated(List<TaskEntity> tasksCreated) {
        this.tasksCreated = tasksCreated;
    }
}
