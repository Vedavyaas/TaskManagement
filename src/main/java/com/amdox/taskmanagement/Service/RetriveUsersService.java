package com.amdox.taskmanagement.Service;

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

    public List<UserDTO> getUsersByOrganization(UserDTO userDTO) {
        if (!userRepository.existsByEmail(userDTO.email())) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(userDTO.email())).equals("ADMIN")) {
            List<UserEntity> userEntities =
                    userRepository.findByOrganization(userDTO.organization());
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

    public List<UserDTO> getUsersByDomain(UserDTO userDTO) {
        if (!userRepository.existsByEmail(userDTO.email())) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(userDTO.email())).equals("ADMIN")) {
            List<UserEntity> userEntities = userRepository.findByDomainAndOrganization(userDTO.domain(), userDTO.organization());

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

    public List<UserDTO> getUserByEmail(UserDTO userDTO, String email) {
        if (!userRepository.existsByEmail(userDTO.email())) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(userDTO.email())).equals("ADMIN")) {
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

    public List<UserDTO> getUsersByUsername(UserDTO userDTO, String username) {
        if (!userRepository.existsByEmail(userDTO.email())) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(userDTO.email())).equals("ADMIN")) {
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

    public List<UserDTO> getUsersByCompanyName(UserDTO userDTO, String companyName) {
        if (!userRepository.existsByEmail(userDTO.email())) {
            return Collections.emptyList();
        }
        if ((userRepository.findRoleByEmail(userDTO.email())).equals("ADMIN")) {
            List<UserEntity> userEntities = userRepository.findByCompanyName(companyName);
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
