package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserOfficialEmail(String userOfficialEmail);
    List<UserProfile> findUserByDesignation(String designation);
    List<UserProfile> findUserByDepartment(String department);
}
