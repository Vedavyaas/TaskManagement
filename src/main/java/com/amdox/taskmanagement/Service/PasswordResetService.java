package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Aspect.LoggingAnnotation;
import com.amdox.taskmanagement.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpService;

    public PasswordResetService(UserRepository userRepository, MailSenderService mailSenderService, PasswordEncoder passwordEncoder, OTPService otpService) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    @LoggingAnnotation("Password reset - OTP generation")
    public String generateOTP(String email) {
        if (userRepository.existsByEmail(email)) {
            var OTP = otpService.createOTP();
            String subject = "Password Reset Verification";
            String body = "Your one-time password (OTP) for password reset is: " + OTP + ". This code is valid for 5 minutes. Please use it to proceed with resetting your password.";
            mailSenderService.sendMail(email, subject, body);
            otpService.updateOtpMap(email, OTP, LocalDateTime.now());
            return "A verification code has been sent to your email.";
        }
        return "No account found with this email address.";
    }

    @LoggingAnnotation("Password reset")
    public String resetPassword(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            userRepository.findByEmail(email).ifPresent(user -> {
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
            });
            otpService.deleteOTP(email);
            return "Your password has been successfully updated.";
        }
        return "No account found with this email address.";
    }

    @LoggingAnnotation("Password reset - OTP verification")
    public boolean verifyOTP(String email, long otp) {
        if (userRepository.existsByEmail(email)) {
            return otpService.verifyOTP(email, otp);
        }
        return false;
    }
}
