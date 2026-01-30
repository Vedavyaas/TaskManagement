package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Entity.Issue;
import com.amdox.taskmanagement.Entity.IssueComment;
import com.amdox.taskmanagement.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping("/create")
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) {
        return ResponseEntity.ok(issueService.createIssue(issue));
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssue(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getIssueById(id));
    }
    
    @GetMapping("/key/{key}")
    public ResponseEntity<Issue> getIssueByKey(@PathVariable String key) {
        return ResponseEntity.ok(issueService.getIssueByKey(key));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody Issue issue) {
        return ResponseEntity.ok(issueService.updateIssue(id, issue));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<IssueComment> addComment(@PathVariable Long id, 
                                                   @RequestParam String authorEmail, 
                                                   @RequestBody String body) {
        return ResponseEntity.ok(issueService.addComment(id, authorEmail, body));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<IssueComment>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getComments(id));
    }
}