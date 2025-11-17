package com.amdox.taskmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByOrganization(String organization);
    List<UserEntity> findByDomainAndOrganization(String domain, String organization);
    @Query("SELECT u.role FROM UserEntity u WHERE u.email = :email")
    String findRoleByEmail(String email);
    List<UserEntity> findByCompanyName(String companyName);
}
