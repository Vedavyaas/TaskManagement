package com.amdox.taskmanagement.Assests;

import java.time.LocalDateTime;

public record TaskDTO(String title, String description, Status status, LocalDateTime createdAt,
                      LocalDateTime updatedAt, LocalDateTime completedAt, LocalDateTime dateDate,
                      Priority priority, String userName, String adminUserName) {
}
