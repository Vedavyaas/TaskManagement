package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Repository.UserEntity;
import com.amdox.taskmanagement.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityUpdateService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntityUpdateService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String updateUsername(String email, String username, String password) {
        if (userRepository.existsByEmail(email)) {
            if (!userRepository.existsByUsername(username)) {
                Optional<UserEntity> userOpt = userRepository.findByEmail(email);
                if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
                    userRepository.updateUsernameByEmail(email, username);
                    return "Username updated successfully.";
                }
                return "Incorrect password.";
            }
            return "Username already exists.";
        }
        return "Email does not exist.";
    }

    public String updateFullName(String email, String fullName, String password) {
        if (userRepository.existsByEmail(email)) {
            Optional<UserEntity> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
                userRepository.updateFullNameByEmail(email, fullName);
                return "Full name updated successfully.";
            }
            return "Incorrect password.";
        }
        return "Email does not exist.";
    }

    public String updateOrganization(String email, String organization, String password) {
        if (userRepository.existsByEmail(email)) {
            Optional<UserEntity> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
                userRepository.updateOrganizationByEmail(organization, email);
                return "Organization name updated successfully.";
            }
            return "Incorrect password.";
        }
        return "Email does not exist.";
    }

    public String updateDomain(String email, String domain, String password) {
        if (userRepository.existsByEmail(email)) {
            Optional<UserEntity> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
                userRepository.updateDomainByEmail(email, domain);
                return "Domain name updated successfully.";
            }
            return "Incorrect password.";
        }
        return "Email does not exist.";
    }

    public String updateCompanyName(String email, String company, String password) {
        if (userRepository.existsByEmail(email)) {
            Optional<UserEntity> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
                userRepository.updateCompanyNameByEmail(email, company);
                return "Company name updated successfully.";
            }
            return "Incorrect password.";
        }
        return "Email does not exist.";
    }
}
