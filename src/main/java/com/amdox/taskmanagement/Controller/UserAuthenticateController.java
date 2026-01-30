package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.DTO.AuthResponseDTO;
import com.amdox.taskmanagement.DTO.LoginRequestDTO;
import com.amdox.taskmanagement.DTO.RegisterRequestDTO;
import com.amdox.taskmanagement.Service.UserAuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticateController {

    @Autowired
    private UserAuthenticateService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok("User register Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
