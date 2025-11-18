package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Controller.NewAccount;
import com.amdox.taskmanagement.Repository.UserEntity;
import com.amdox.taskmanagement.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class UserAccountCreationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final Map<String, Long> map = new HashMap<>();

    public UserAccountCreationService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }


    public String createAccount(NewAccount newAccount, String OTP) {
        if (map.containsKey(newAccount.email())) {
            if (Objects.equals(map.get(newAccount.email()), Long.valueOf(OTP))) {
                userRepository.save(new UserEntity(newAccount.username(),
                        passwordEncoder.encode(newAccount.password()), newAccount.email(),
                        newAccount.fullName(), "USER", newAccount.organization(),
                        newAccount.email(), newAccount.companyName()));
                map.remove(newAccount.email());
                return "Account created successfully.";
            }
        }
        return "The provided OTP does not match.";
    }

    public String verifyAccount(NewAccount newAccount) {
        if (userRepository.existsByEmail(newAccount.email())) return "An account with this email already exists.";
        else if (userRepository.existsByUsername(newAccount.username())) return "This username is already taken.";
        else {
            Random random = new Random();
            long OTP = 100000 + random.nextInt(900000);
            map.put(newAccount.email(), OTP);
            mailSenderService.sendMail(newAccount.email(), "Account Creation Verification", "Your one-time password (OTP) for account creation is: " + OTP + ". Please use this code to complete your registration.");
            return "A verification code has been sent to " + newAccount.email() + ". Please check your email and enter the code to proceed.";
        }
    }
}
