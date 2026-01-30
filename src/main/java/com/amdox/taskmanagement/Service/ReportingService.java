package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Entity.Issue;
import com.amdox.taskmanagement.Entity.Sprint;
import com.amdox.taskmanagement.Enum.IssueStatus;
import com.amdox.taskmanagement.Enum.SprintState;
import com.amdox.taskmanagement.Repository.IssueRepository;
import com.amdox.taskmanagement.Repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    @Autowired
    private IssueRepository issueRepo;
    @Autowired
    private SprintRepository sprintRepo;

    public Map<String, Object> burndown(Long sprintId) {
        Sprint sprint = sprintRepo.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("sprint not found"));
        List<Issue> issues = issueRepo.findBySprintId(sprintId);

        int totalTask = issues.size();
        Map<String, Object> chart = new LinkedHashMap<>();

        LocalDateTime start = sprint.getStartDate();
        LocalDateTime end = sprint.getEndDate() != null ? sprint.getEndDate() : LocalDateTime.now();

        if (start == null) start = LocalDateTime.now();

        for (LocalDateTime date = start; !date.isAfter(end); date = date.plusDays(1)) {
            int done = (int) issues.stream().filter(i -> "DONE".equals(i.getStatus().name())).count();
            chart.put(date.toString(), totalTask - done);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("sprintId", sprintId);
        response.put("burnDown", chart);

        return response;
    }

    public Map<String, Object> velocity(Long projectId) {
        List<Sprint> completedSprints = sprintRepo.findByProjectId(projectId)
                .stream()
                .filter(s -> s.getState() == SprintState.COMPLETE)
                .collect(Collectors.toList());

        Map<String, Integer> velocity = new LinkedHashMap<>();
        for (Sprint sprint : completedSprints) {
            int doneIssues = (int) issueRepo.findBySprintId(sprint.getId())
                    .stream()
                    .filter(i -> i.getStatus() == IssueStatus.DONE)
                    .count();

            velocity.put(sprint.getSprintName(), doneIssues);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("projectId", projectId);
        response.put("velocity", velocity);

        return response;
    }

    public Map<String, Object> sprintRepot(Long sprintId) { // Keeping typo 'sprintRepot' as per user code if Controller calls it? 
        // User Controller in step 72 calls `sprintRepot`. So I must keep it.
        List<Issue> issues = issueRepo.findBySprintId(sprintId);

        long completed = issues.stream().filter(i -> i.getStatus() == IssueStatus.DONE).count();

        Map<String, Object> response = new HashMap<>();
        response.put("totalIssues", issues.size());
        response.put("completed", completed);
        response.put("incomplete", issues.size() - completed);
        return response;
    }

    public Map<String, Object> epicReport(Long epicId) {
        List<Issue> stories = issueRepo.findByEpicId(epicId);

        long completed = stories.stream().filter(i -> i.getStatus() == IssueStatus.DONE).count();

        int progress = stories.isEmpty() ? 0 : (int) (completed * 100 / stories.size());

        Map<String, Object> response = new HashMap<>();

        response.put("epicId", epicId);
        response.put("totalStories", stories.size());
        response.put("completedStories", completed);
        response.put("progressPercentage", progress);

        return response;
    }

    public Map<String, Object> workLoad(Long sprintId) {
        List<Issue> issues = issueRepo.findBySprintId(sprintId);

        Map<String, Long> workload = issues.stream()
                .collect(Collectors.groupingBy(issue -> issue.getAssigneeEmail() != null ? issue.getAssigneeEmail() : "Unassigned", Collectors.counting()));

        Map<String, Object> response = new HashMap<>();
        response.put("workload", workload);

        return response;
    }

    public Map<String, Object> cumulativeFlow(Long sprintId) {
        List<Issue> issues = issueRepo.findBySprintId(sprintId);

        Map<IssueStatus, Long> cfd = issues.stream()
                .collect(Collectors.groupingBy(Issue::getStatus, Collectors.counting()));

        Map<String, Object> response = new HashMap<>();
        response.put("cumulativeFlow", cfd);

        return response;
    }
}
