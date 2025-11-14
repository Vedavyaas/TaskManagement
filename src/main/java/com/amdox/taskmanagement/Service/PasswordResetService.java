package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final Map<String, OTPData> otpMap = new HashMap<>();
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(UserRepository userRepository, MailSenderService mailSenderService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateOTP(String email) {
        if(userRepository.existsByEmail(email)){
            var OTP = createOTP();
            String subject = "Password Reset Verification";
            String body = "Your one-time password (OTP) for password reset is: " + OTP + ". This code is valid for 5 minutes. Please use it to proceed with resetting your password.";
            mailSenderService.sendMail(email, subject, body);
            otpMap.put(email, new OTPData(OTP, LocalDateTime.now()));
            return "A verification code has been sent to your email.";
        }
        return "No account found with this email address.";
    }

    private Long createOTP() {
        Random random = new Random();
        return (long) (100000 + random.nextInt(900000));
    }

    public boolean verifyOTP(String email, Long OTP){
        OTPData otpData = otpMap.get(email);
        return otpData != null && otpData.otp.equals(OTP) && !isExpired(otpData.timestamp);
    }

    private boolean isExpired(LocalDateTime timestamp) {
        return timestamp.isBefore(LocalDateTime.now().minusMinutes(5));
    }

    public String resetPassword(String email,  String password) {
        if(userRepository.existsByEmail(email)){
            userRepository.findByEmail(email).ifPresent(user -> {
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
            });
            otpMap.remove(email);
            return "Your password has been successfully updated.";
        }
        return "No account found with this email address.";
    }

    private static class OTPData {
        Long otp;
        LocalDateTime timestamp;

        OTPData(Long otp, LocalDateTime timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
    }
}
