package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Assests.Type;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type contentType;

    private String fileName;

    private LocalDateTime createdAt;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "uploade_by_id")
    private UserEntity uploadedBy;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private IssueEntity issue;

    public AttachmentEntity() {}

    public AttachmentEntity(String fileName, Type contentType, String fileUrl, UserEntity uploadedBy, TaskEntity task, IssueEntity issue) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileUrl = fileUrl;
        this.uploadedBy = uploadedBy;
        this.task = task;
        this.issue = issue;
    }

    public Type getContentType() {
        return contentType;
    }

    public void setContentType(Type contentType) {
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

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public IssueEntity getIssue() {
        return issue;
    }

    public void setIssue(IssueEntity issue) {
        this.issue = issue;
    }
}
