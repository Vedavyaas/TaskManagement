package com.amdox.taskmanagement.Repository;

import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
    List<IssueEntity> findIssueEntitiesByIssuedUser(UserEntity user);
    List<IssueEntity> findIssueEntitiesByIssuedTo(UserEntity user);

    boolean existsByTitle(String title);

    @Modifying
    @Query("DELETE FROM IssueEntity i WHERE i.issueToken = :token")
    void deleteIssueEntitiesByIssueToken(String token);

    Optional<IssueEntity> findIssueEntitiesByTitle(String title);

    boolean existsByIssueToken(String issueToken);

    @Modifying
    @Query("UPDATE IssueEntity i SET i.description = :description WHERE i.issueToken = :token")
    void updateIssueDescriptionByIssueToken(String description, String token);

    @Modifying
    @Query("UPDATE IssueEntity i SET i.comments = :comment WHERE i.issueToken = :issueToken")
    void updateIssueCommentByIssueToken(String comment, String issueToken);

    @Modifying
    @Query("UPDATE IssueEntity i SET i.status = :status WHERE i.issueToken = :issueToken")
    void updateIssueStatusByIssueToken(String status, String issueToken);
}
