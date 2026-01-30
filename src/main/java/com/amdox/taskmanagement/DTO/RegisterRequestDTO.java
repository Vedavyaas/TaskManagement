package com.amdox.taskmanagement.DTO;

import com.amdox.taskmanagement.Enum.Role;

public class RegisterRequestDTO {
    public String userName;
    public String userOfficialEmail;
    public String password;
    public Role role;

    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String userName, String userOfficialEmail, String password, Role role) {
        this.userName = userName;
        this.userOfficialEmail = userOfficialEmail;
        this.password = password;
        this.role = role;
    }
}
