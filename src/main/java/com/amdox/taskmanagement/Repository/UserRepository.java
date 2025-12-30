package com.amdox.taskmanagement.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("UPDATE UserEntity u SET u.username = :username WHERE u.email = :email")
    void updateUsernameByEmail(String email, String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.fullName = :fullName WHERE u.email = :email")
    void updateFullNameByEmail(String email, String fullName);

    @Modifying
    @Query("UPDATE UserEntity u SET u.organization = :organization WHERE u.email = :email")
    void updateOrganizationByEmail(String organization, String email);

    @Modifying
    @Query("UPDATE UserEntity u SET u.domain = :domain WHERE u.email = :email")
    void updateDomainByEmail(String email, String domain);

    @Modifying
    @Query("UPDATE UserEntity u SET u.companyName = :companyName WHERE u.email = :email")
    void updateCompanyNameByEmail(String email, String companyName);

    @Query("SELECT u.organization FROM UserEntity u WHERE u.username = :user")
    String findOrganizationByUsername(String user);
}
