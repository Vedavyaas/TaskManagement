package com.amdox.taskmanagement.Controller;

public record UserDTO(String username, String email, String fullName,
                      String organization, String domain, String companyName) {
}
