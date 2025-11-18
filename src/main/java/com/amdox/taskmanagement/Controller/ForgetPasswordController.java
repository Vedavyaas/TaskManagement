package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Service.PasswordResetService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgetPasswordController {

    private final PasswordResetService passwordResetService;

    public ForgetPasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/generate/otp")
    public String resetPassword(@RequestParam String email) {
        return passwordResetService.generateOTP(email);
    }

    @PutMapping("/reset/password")
    public String resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String password) {
        if (passwordResetService.verifyOTP(email, Long.parseLong(otp))) {
            return passwordResetService.resetPassword(email, password);
        }
        return "The verification code is incorrect or has expired.";
    }
}
