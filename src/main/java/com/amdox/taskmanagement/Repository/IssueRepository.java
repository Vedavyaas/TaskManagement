package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Entity.Issue;
import com.amdox.taskmanagement.Enum.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByIssueKey(String issueKey);
    List<Issue> findByAssigneeEmail(String assigneeEmail);
    List<Issue> findBySprintId(Long sprintId);

    List<Issue> findByStatus(IssueStatus status);
    
    List<Issue> findByEpicId(Long epicId); // Added for ReportingService
}
