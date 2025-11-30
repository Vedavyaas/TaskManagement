package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Assests.Type;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contentType;

    private String fileName;

    private LocalDateTime createdAt;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "uploade_by_id")
    private UserEntity uploadedBy;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private IssueEntity issue;

    public AttachmentEntity() {}

    public AttachmentEntity(String fileName, String contentType, String fileUrl, UserEntity uploadedBy, IssueEntity issue) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileUrl = fileUrl;
        this.uploadedBy = uploadedBy;
        this.issue = issue;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public UserEntity getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserEntity uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public IssueEntity getIssue() {
        return issue;
    }

    public void setIssue(IssueEntity issue) {
        this.issue = issue;
    }
}
