package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Assests.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;

    private String comments;

    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "issued_by_id")
    private UserEntity issuedUser;
    private String issueToken;

    @ManyToOne
    @JoinColumn(name = "issued_to_id")
    private UserEntity issuedTo;

    public IssueEntity() {
    }

    public IssueEntity(String title, String description, String comments, UserEntity issuedUser, UserEntity issuedTo) {
        this.title = title;
        this.description = description;
        this.comments = comments;
        this.status = Status.ACTIVE;
        this.issuedUser = issuedUser;
        this.issuedTo = issuedTo;
        this.issueToken = issuedUser.getCompanyName() + issuedUser.getEmail() + LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comment) {
        this.comments = comment;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public UserEntity getIssuedUser() {
        return issuedUser;
    }

    public void setIssuedUser(UserEntity issuedUser) {
        this.issuedUser = issuedUser;
    }

    public String getIssueToken() {
        return issueToken;
    }

    public void setIssueToken(String issueToken) {
        this.issueToken = issueToken;
    }

    public UserEntity getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(UserEntity issuedTo) {
        this.issuedTo = issuedTo;
    }
}
