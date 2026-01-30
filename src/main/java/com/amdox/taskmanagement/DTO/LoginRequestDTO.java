package com.amdox.taskmanagement.DTO;

public class LoginRequestDTO {
    public String useOfficialEmail; // Kept spelling from source 'useOfficialEmail'
    public String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String useOfficialEmail, String password) {
        this.useOfficialEmail = useOfficialEmail;
        this.password = password;
    }
}
