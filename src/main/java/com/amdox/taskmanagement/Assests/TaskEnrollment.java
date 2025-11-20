package com.amdox.taskmanagement.Assests;

import java.time.LocalDateTime;
public record TaskEnrollment(String title, String description, LocalDateTime dueDate,
                             Priority priority, String assignedUserEmail,
                             String createdUserEmail) {
}
