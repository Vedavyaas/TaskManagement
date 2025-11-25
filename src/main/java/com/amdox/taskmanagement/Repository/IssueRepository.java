package com.amdox.taskmanagement.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {

    List<IssueEntity> findIssueEntitiesByIssuedUser(UserEntity user);

    @Query("SELECT i FROM IssueEntity i WHERE i.issuedUser = :user AND i.title = :title")
    Optional<IssueEntity> findIssueEntitiesByIssuedUserAndTitle(UserEntity user, String title);

    boolean existsByTitle(String title);

    @Modifying
    @Transactional
    void deleteIssueEntitiesByTitle(String title);
}
