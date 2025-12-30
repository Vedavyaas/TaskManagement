package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Aspect.LoggingAnnotation;
import com.amdox.taskmanagement.Assests.UserDTO;
import com.amdox.taskmanagement.Repository.UserEntity;
import com.amdox.taskmanagement.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RetriveUsersService {
    private final UserRepository userRepository;

    public RetriveUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @LoggingAnnotation("Retrived users by organization")
    public List<UserDTO> getUsersByOrganization(String userName) {
        Optional<UserEntity> admin = userRepository.findByUsername(userName);
        if (admin.isEmpty()) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(admin.get().getEmail())).equals("ADMIN")) {
            List<UserEntity> userEntities =
                    userRepository.findByOrganization(admin.get().getOrganization());
            return userEntities.stream()
                    .map(u -> new UserDTO(
                            u.getUsername(),
                            u.getEmail(),
                            u.getFullName(),
                            u.getOrganization(),
                            u.getDomain(),
                            u.getCompanyName()
                    ))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    @LoggingAnnotation("Retrived user by domain")
    public List<UserDTO> getUsersByDomain(String userName) {
        Optional<UserEntity> admin = userRepository.findByUsername(userName);
        if (admin.isEmpty()) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(admin.get().getEmail())).equals("ADMIN")) {
            List<UserEntity> userEntities = userRepository.findByDomainAndOrganization(admin.get().getDomain(), admin.get().getOrganization());
            return userEntities.stream()
                    .map(u -> new UserDTO(
                            u.getUsername(),
                            u.getEmail(),
                            u.getFullName(),
                            u.getOrganization(),
                            u.getDomain(),
                            u.getCompanyName()
                    ))
                    .toList();
        }
        return Collections.emptyList();
    }

    @LoggingAnnotation("Retrived user by email")
    public List<UserDTO> getUserByEmail(String userName, String email) {
        Optional<UserEntity> admin = userRepository.findByUsername(userName);
        if (admin.isEmpty()) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(admin.get().getEmail()).equals("ADMIN"))) {
            Optional<UserEntity> userEntities = userRepository.findByEmail(email);
            return userEntities.stream()
                    .map(u -> new UserDTO(
                            u.getUsername(),
                            u.getEmail(),
                            u.getFullName(),
                            u.getOrganization(),
                            u.getDomain(),
                            u.getCompanyName()
                    ))
                    .toList();
        }
        return Collections.emptyList();
    }

    @LoggingAnnotation("Retrived user by username")
    public List<UserDTO> getUsersByUsername(String adminName, String username) {
        Optional<UserEntity> admin = userRepository.findByUsername(adminName);
        if (admin.isEmpty()) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(admin.get().getEmail())).equals("ADMIN")) {
            Optional<UserEntity> userEntities = userRepository.findByUsername(username);
            return userEntities.stream()
                    .map(u -> new UserDTO(
                            u.getUsername(),
                            u.getEmail(),
                            u.getFullName(),
                            u.getOrganization(),
                            u.getDomain(),
                            u.getCompanyName()
                    ))
                    .toList();
        }
        return Collections.emptyList();
    }

    @LoggingAnnotation("Retrived user by company name")
    public List<UserDTO> getUsersByCompanyName(String userName) {
        Optional<UserEntity> admin = userRepository.findByUsername(userName);
        if (admin.isEmpty()) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(admin.get().getEmail()).equals("ADMIN"))) {
            List<UserEntity> userEntities = userRepository.findByCompanyName(admin.get().getCompanyName());
            return userEntities.stream()
                    .map(u -> new UserDTO(
                            u.getUsername(),
                            u.getEmail(),
                            u.getFullName(),
                            u.getOrganization(),
                            u.getDomain(),
                            u.getCompanyName()
                    ))
                    .toList();
        }
        return Collections.emptyList();
    }

    @LoggingAnnotation("Retrived user by email")
    public List<UserDTO> getUserByEmailPublic(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userEntity.stream()
                .map(u -> new UserDTO(
                        u.getUsername(),
                        u.getEmail(),
                        u.getFullName(),
                        null,
                        null,
                        null
                ))
                .toList();
    }

    @LoggingAnnotation("Retrived user by username")
    public List<UserDTO> getUserByUsernamePublic(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.stream()
                .map(u -> new UserDTO(
                        u.getUsername(),
                        u.getEmail(),
                        u.getFullName(),
                        null,
                        null,
                        null
                ))
                .toList();
    }
}
