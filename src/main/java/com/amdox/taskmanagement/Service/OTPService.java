package com.amdox.taskmanagement.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService {
    private final Map<String, OTPService.OTPData> otpMap = new HashMap<>();

    public void updateOtpMap(String email, Long otp, LocalDateTime timestamp) {
        otpMap.put(email, new OTPService.OTPData(otp, timestamp));
    }

    public Long createOTP() {
        Random random = new Random();
        return (long) (100000 + random.nextInt(900000));
    }

    public void deleteOTP(String email) {
        otpMap.remove(email);
    }

    public boolean verifyOTP(String email, Long OTP) {
        OTPService.OTPData otpData = otpMap.get(email);
        return otpData != null && otpData.otp.equals(OTP) && !isExpired(otpData.timestamp);
    }

    public boolean isExpired(LocalDateTime timestamp) {
        return timestamp.isBefore(LocalDateTime.now().minusMinutes(5));
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
