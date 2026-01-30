package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Entity.Issue;
import com.amdox.taskmanagement.Entity.IssueComment;
import com.amdox.taskmanagement.Repository.IssueCommentRepository;
import com.amdox.taskmanagement.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private IssueCommentRepository commentRepo;

    public Issue createIssue(Issue issue) {
        // Ensure Key generation or other logic if needed
        return issueRepo.save(issue);
    }

    public Issue getIssueById(Long id) {
        return issueRepo.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
    }
    
    public Issue getIssueByKey(String key) {
        return issueRepo.findByIssueKey(key).orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    public List<Issue> getAllIssues() {
        return issueRepo.findAll();
    }

    public Issue updateIssue(Long id, Issue updatedIssue) {
        Issue existing = getIssueById(id);
        
        existing.setIssueTitle(updatedIssue.getIssueTitle());
        existing.setDesription(updatedIssue.getDesription());
        existing.setIssueType(updatedIssue.getIssueType());
        existing.setPriority(updatedIssue.getPriority());
        existing.setStatus(updatedIssue.getStatus());
        existing.setAssigneeEmail(updatedIssue.getAssigneeEmail());
        existing.setRepoterEmail(updatedIssue.getRepoterEmail());
        existing.setDueDate(updatedIssue.getDueDate());
        existing.setSprintId(updatedIssue.getSprintId());
        existing.setEpicId(updatedIssue.getEpicId());
        existing.setUpdateAt(LocalDateTime.now());
        
        return issueRepo.save(existing);
    }

    public void deleteIssue(Long id) {
        issueRepo.deleteById(id);
    }

    public IssueComment addComment(Long issueId, String authorEmail, String body) {
        if (!issueRepo.existsById(issueId)) {
            throw new RuntimeException("Issue not found");
        }
        IssueComment comment = new IssueComment();
        comment.setIssueId(issueId);
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);
        comment.setCreatedAt(LocalDateTime.now());
        
        return commentRepo.save(comment);
    }

    public List<IssueComment> getComments(Long issueId) {
        return commentRepo.findByIssueIdOrderByCreatedAt(issueId);
    }
}
