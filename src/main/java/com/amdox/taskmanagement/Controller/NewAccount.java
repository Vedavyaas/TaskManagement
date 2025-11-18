package com.amdox.taskmanagement.Controller;

public record NewAccount(String username, String password, String email,
                         String fullName, String organization, String domain, String companyName) {
}
