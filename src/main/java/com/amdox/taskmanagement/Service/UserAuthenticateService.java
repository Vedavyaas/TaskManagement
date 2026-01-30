package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.DTO.AuthResponseDTO;
import com.amdox.taskmanagement.DTO.LoginRequestDTO;
import com.amdox.taskmanagement.DTO.RegisterRequestDTO;
import com.amdox.taskmanagement.Entity.UserAuthenticate;
import com.amdox.taskmanagement.Repository.UserAuthenticateRepository;
import com.amdox.taskmanagement.Security.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticateService {

    @Autowired
    private UserAuthenticateRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncode; // Fixed name to match user code usage or standard? User used 'passwordEncode'

    @Autowired
    private JWTTokenUtil jwtUtil;

    public void register(RegisterRequestDTO req) {
        if (userRepo.findByUserOfficialEmail(req.userOfficialEmail).isPresent()) {
            throw new RuntimeException("Profile already Exists: " + req.userOfficialEmail);
        }
        UserAuthenticate user = new UserAuthenticate();

        user.setUserName(req.userName);
        user.setUserOfficialEmail(req.userOfficialEmail);
        user.setPassword(passwordEncode.encode(req.password));
        user.setRole(req.role);

        userRepo.save(user);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        UserAuthenticate user = userRepo.findByUserOfficialEmail(dto.useOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncode.matches(dto.password, user.getPassword())) {
            throw new RuntimeException("Invalid Credential");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDTO(token, "Token generated successfully");
    }
}
