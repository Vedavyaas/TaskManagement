package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Assests.IssueDTO;
import com.amdox.taskmanagement.Assests.IssueEnrollment;
import com.amdox.taskmanagement.Service.IssueService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issue/get")
    public List<IssueDTO> getIssue(@RequestParam String username){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.getIssuesByUserName(username, admin);
    }

    @PostMapping("/issue/create")
    public String createIssue(@RequestBody IssueEnrollment issueEnrollment){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.createIssue(issueEnrollment, userName);
    }

    @PutMapping("issue/update")
    public String updateIssue(@RequestBody IssueEnrollment issueEnrollment){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.updateIssue(issueEnrollment, userName);
    }

    @DeleteMapping("/issue/close")
    public String deleteIssue(@RequestBody IssueEnrollment issueEnrollment){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.closeIssue(issueEnrollment, userName);
    }
}

