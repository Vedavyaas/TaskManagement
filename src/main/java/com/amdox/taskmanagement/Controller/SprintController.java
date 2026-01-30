package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Entity.Issue;
import com.amdox.taskmanagement.Entity.Sprint;
import com.amdox.taskmanagement.Service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping("/create")
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) {
        return ResponseEntity.ok(sprintService.createSprint(sprint));
    }

    @PutMapping("/assign/{sprintId}/{issueId}")
    public ResponseEntity<Issue> assignedIssueToSprint(@PathVariable Long sprintId, @PathVariable Long issueId) {
        return ResponseEntity.ok(sprintService.assignIssueToSprint(sprintId, issueId));
    }

    @PutMapping("/{sprintId}/start")
    public ResponseEntity<Sprint> startSprint(@PathVariable Long sprintId) {
        return ResponseEntity.ok(sprintService.startSprint(sprintId));
    }

    @PutMapping("/{sprintId}/close")
    public ResponseEntity<Sprint> closeSprint(@PathVariable Long sprintId) {
        return ResponseEntity.ok(sprintService.closeSprint(sprintId)); // Fixed method name 'closeSrint' to 'closeSprint' in Service
    }

    @GetMapping("/{sprintId}/burnDown")
    public ResponseEntity<Map<String, Object>> getBurnDown(@PathVariable Long sprintId) {
        return ResponseEntity.ok(sprintService.getBurnDownDate(sprintId));
    }
}
