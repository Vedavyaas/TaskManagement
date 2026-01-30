package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Entity.UserAuthenticate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthenticateRepository extends JpaRepository<UserAuthenticate, Long> {
    Optional<UserAuthenticate> findByUserOfficialEmail(String userOfficialEmail);
}
