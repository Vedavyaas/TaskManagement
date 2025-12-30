package com.amdox.taskmanagement.Repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LoggingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime timestamp;

    private String username;

    private String organization;

    private String message;

    public LoggingEntity() {}

    public LoggingEntity(String username, String organization, String message) {
        this.username = username;
        this.organization = organization;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
