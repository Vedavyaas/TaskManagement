package com.amdox.taskmanagement.Assests;

import java.time.LocalDateTime;

public record TaskDisplayer(String title, String description, Status status, LocalDateTime createdAt,
                            LocalDateTime updatedAt, LocalDateTime completedAt, LocalDateTime dateDate,
                            Priority priority, String assignedUserEmail, String createdUserEmail) {
}
