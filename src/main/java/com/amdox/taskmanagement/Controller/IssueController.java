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

    @GetMapping("/get/issueToken")
    public String getIssueToken(@RequestParam String title){
        return  issueService.getIssueTokenByTitle(title);
    }

    @GetMapping("/issue/get")
    public List<IssueDTO> getIssueOfAnotherUser(@RequestParam String username){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.getIssuesByUserName(username, admin);
    }

    @GetMapping("/issue/get/self")
    public List<IssueDTO> getIssueOfUser(){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.getIssuesByUserName(admin);
    }
    @PostMapping("/issue/create")
    public String createIssue(@RequestBody IssueEnrollment issueEnrollment){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.createIssue(issueEnrollment, userName);
    }

    @DeleteMapping("/issue/close")
    public String deleteIssue(@RequestParam String issueToken){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.closeIssue(issueToken, admin);
    }

    @PutMapping("/issue/update/description")
    public String updateIssueDescription(@RequestParam String description, @RequestParam String issueToken){
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.updateIssueByDescription(description, issueToken, user);
    }

    @PutMapping("/issue/update/description/other")
    public String updateIssueDescription(@RequestParam String description, @RequestParam String issueToken, @RequestParam String user){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.updateIssueByDescription(description, issueToken, user, admin);
    }

    @PutMapping("/issue/update/comment")
    public String updateIssueComment(@RequestParam String comment, @RequestParam String issueToken){
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.updateIssueByComment(comment, issueToken, user);
    }

    @PutMapping("/issue/update/comment/other")
    public String updateIssueComment(@RequestParam String comment, @RequestParam String issueToken, @RequestParam String user){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.updateIssueByComment(comment, issueToken, user, admin);
    }

    @PutMapping("/issue/update/status")
    public String updateIssueStatus(@RequestParam String status, @RequestParam String issueToken){
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.updateIssueByStatus(status, issueToken, user);
    }

    @PutMapping("/issue/update/status/other")
    public String updateIssueStatus(@RequestParam String status, @RequestParam String issueToken, @RequestParam String user){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return issueService.updateIssueByStatus(status, issueToken, user, admin);
    }
}

