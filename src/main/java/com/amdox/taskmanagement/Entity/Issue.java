package com.amdox.taskmanagement.Entity;

import com.amdox.taskmanagement.Enum.IssuePriority;
import com.amdox.taskmanagement.Enum.IssueStatus;
import com.amdox.taskmanagement.Enum.IssueType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="issues")
public class Issue {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String issueKey;

    @Column(nullable=false)
    private String issueTitle;

    @Column(length=10000)
    private String desription; // Kept spelling from source 'desription'

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    private String assigneeEmail;
    private String repoterEmail; // Kept spelling from source 'repoterEmail'

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updateAt = LocalDateTime.now();

    private LocalDateTime dueDate;

    private Long sprintId;
    private Long epicId;
    
    // Additional field for backlog position logic used in SprintService
    private Integer backLogPosition; 

    public Issue() {
    }
    
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    public String getRepoterEmail() {
        return repoterEmail;
    }

    public void setRepoterEmail(String repoterEmail) {
        this.repoterEmail = repoterEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getEpicId() {
        return epicId;
    }

    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    public Integer getBackLogPosition() {
        return backLogPosition;
    }

    public void setBackLogPosition(Integer backLogPosition) {
        this.backLogPosition = backLogPosition;
    }
}
