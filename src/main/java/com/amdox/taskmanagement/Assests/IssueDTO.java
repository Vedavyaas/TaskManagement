package com.amdox.taskmanagement.Assests;

public record IssueDTO (String title, String description, String comment,
                        String issuedTo, String issuedBy, Status status) {
}