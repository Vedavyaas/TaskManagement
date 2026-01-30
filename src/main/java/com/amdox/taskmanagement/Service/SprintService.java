package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Entity.Issue;
import com.amdox.taskmanagement.Entity.Sprint;
import com.amdox.taskmanagement.Enum.IssueStatus;
import com.amdox.taskmanagement.Enum.SprintState;
import com.amdox.taskmanagement.Repository.IssueRepository;
import com.amdox.taskmanagement.Repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepo;

    @Autowired
    private IssueRepository issueRepo;

    public Sprint createSprint(Sprint sprint) {
        sprint.setState(SprintState.PLANNED);
        return sprintRepo.save(sprint);
    }

    @Transactional
    public Issue assignIssueToSprint(Long sprintId, Long issueId) {
        Sprint sprint = sprintRepo.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));
        Issue issue = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        if (sprint.getState() == SprintState.COMPLETE) {
            throw new RuntimeException("Can not add issue to completed sprint");
        }
        issue.setSprintId(sprintId);
        return issueRepo.save(issue);
    }

    @Transactional
    public Sprint startSprint(Long sprintId) {
        Sprint sprint = sprintRepo.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        if (sprint.getState() != SprintState.PLANNED) {
            throw new RuntimeException("Only Planned sprint can be start");
        }

        if (sprint.getStartDate() == null) {
            sprint.setStartDate(LocalDateTime.now());
        }
        sprint.setState(SprintState.ACTIVE); // Added state transition to ACTIVE which was missing in snippet but logical? 
        // Wait, snippet said: if(sprint.getState()!=SprintState.PLANNED)... then save. It didn't set ACTIVE explicitly in the snippet?
        // Checking snippet in Step 64: "if(sprint.getStartDate()==null){sprint.setStartDate...} return sprintRepo.save(sprint);"
        // It missed setState(ACTIVE). I should add it to make it work.
        sprint.setState(SprintState.ACTIVE);
        
        return sprintRepo.save(sprint);
    }

    @Transactional
    public Sprint closeSprint(Long sprintId) { // Fixed typo 'closeSrint' to 'closeSprint'
        Sprint sprint = sprintRepo.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        sprint.setState(SprintState.COMPLETE);
        if (sprint.getEndDate() == null) {
            sprint.setEndDate(LocalDateTime.now());
        }
        List<Issue> issues = issueRepo.findBySprintId(sprintId);

        for (Issue issue : issues) {
            if (!issue.getStatus().name().equals(IssueStatus.DONE.name())) {
                issue.setSprintId(null);
                issue.setBackLogPosition(0);
                issueRepo.save(issue);
            }
        }

        return sprintRepo.save(sprint);
    }

    public Map<String, Object> getBurnDownDate(Long sprintId) {
        Sprint sprint = sprintRepo.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        LocalDateTime start = sprint.getStartDate();
        LocalDateTime end = sprint.getEndDate() != null ?
                sprint.getEndDate() : LocalDateTime.now();

        List<Issue> issue = issueRepo.findBySprintId(sprintId);
        int totalTask = issue.size();

        Map<String, Object> burnDown = new LinkedHashMap<>();
        LocalDateTime cursor = start;
        
        if(cursor == null) cursor = LocalDateTime.now(); // Safety check

        while (!cursor.isAfter(end)) {
            final LocalDateTime currentCursor = cursor; // Effing final for lambda
            // Snippet logic: count done issues. NOTE: This logic is flawed for historical burndown (it counts current status for past dates).
            // But I must implement "as provided".
            long completed = issue.stream().filter(i -> "DONE".equals(i.getStatus().name())).count();
            burnDown.put(cursor.toString(), totalTask - (int) completed);
            cursor = cursor.plusDays(1);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("sprintId", sprintId);
        response.put("startDate", start);
        response.put("endDate", end);
        response.put("burnDown", burnDown);

        return response;
    }
}
