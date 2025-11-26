package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Assests.IssueDTO;
import com.amdox.taskmanagement.Assests.IssueEnrollment;
import com.amdox.taskmanagement.Repository.IssueEntity;
import com.amdox.taskmanagement.Repository.IssueRepository;
import com.amdox.taskmanagement.Repository.UserEntity;
import com.amdox.taskmanagement.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueService(IssueRepository issueRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public String getIssueTokenByTitle(String title){
        if(issueRepository.existsByTitle(title)){
            Optional<IssueEntity> issue = issueRepository.findIssueEntitiesByTitle(title);
            return issue.get().getIssueToken();
        }
        return "Issue not found";
    }

    public List<IssueDTO> getIssuesByUserName(String userName, String admin) {
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> user = userRepository.findByUsername(userName);

        if (adminUser.isPresent() && user.isPresent()) {
            if ("ADMIN".equals(adminUser.get().getRole())) {
                List<IssueEntity> issues = issueRepository.findIssueEntitiesByIssuedUser(user.get());
                return issues.stream().map(issue -> new IssueDTO(issue.getTitle(), issue.getDescription(), issue.getComments(), admin, userName, issue.getStatus())).collect(Collectors.toList());
            }
            return null;
        }
        return null;
    }

    public List<IssueDTO> getIssuesByUserName(String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            List<IssueEntity> issues = issueRepository.findIssueEntitiesByIssuedTo(user.get());
            return issues.stream().map(issue -> new IssueDTO(issue.getTitle(), issue.getDescription(), issue.getComments(), issue.getIssuedTo().getUsername(), issue.getIssuedUser().getUsername(), issue.getStatus())).collect(Collectors.toList());
        }
        return null;
    }

    public String createIssue(IssueEnrollment issueEnrollment, String admin) {
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> assignedToUser = userRepository.findByUsername(issueEnrollment.issuedTo());
        if (assignedToUser.isPresent() && adminUser.isPresent()) {
            if (adminUser.get().getRole().equals("ADMIN")) {
                if(!issueRepository.existsByTitle(issueEnrollment.title())){
                    IssueEntity issueEntity = new IssueEntity(issueEnrollment.title(), issueEnrollment.description(), issueEnrollment.comment(), adminUser.get(), assignedToUser.get());
                    issueRepository.save(issueEntity);
                    return "Issue created successfully.";
                }
                return "Issue with title already exists.";
            }
            return "You do not have permission to perform this action.";
        }
        return "User do not exist.";
    }

    @Transactional
    public String closeIssue(String issueToken, String admin) {
        if (issueRepository.existsByIssueToken(issueToken)) {
            Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
            if (adminUser.isPresent() && "ADMIN".equals(adminUser.get().getRole())) {
                issueRepository.deleteIssueEntitiesByIssueToken(issueToken);
                return "Issue closed successfully.";
            }
            return "You don't have permission to perform this action.";
        }
        return "Issue does not exist.";
    }

    @Transactional
    public String updateIssueByDescription(String description, String token, String user) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent()) {
            if(issueRepository.existsByIssueToken(token)) {
                issueRepository.updateIssueDescriptionByIssueToken(description, token);
                return "Issue updated successfully.";
            }
            return "Issue does not exist.";
        }
        return "User do not exist.";
    }

    @Transactional
    public String updateIssueByDescription(String description, String token, String user, String admin) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (adminUser.get().getRole().equals("ADMIN")) {
                if(!issueRepository.existsByIssueToken(token)) {
                    issueRepository.updateIssueDescriptionByIssueToken(description, token);
                }
                return "Issue doesnt exist.";
            }
            return "You do not have permission to perform this action.";
        }
        return "User do not exist.";
    }

    @Transactional
    public String updateIssueByComment(String comment, String issueToken, String user) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent()) {
            if(issueRepository.existsByIssueToken(issueToken)) {
                issueRepository.updateIssueCommentByIssueToken(comment, issueToken);
                return "Issue updated successfully.";
            }
            return "Issue does not exist.";
        }
        return "User do not exist.";
    }

    @Transactional
    public String updateIssueByComment(String comment, String issueToken, String user, String admin) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);

        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (adminUser.get().getRole().equals("ADMIN")) {
                if(!issueRepository.existsByIssueToken(issueToken)) {
                    issueRepository.updateIssueCommentByIssueToken(comment, issueToken);
                    return "Issue updated successfully.";
                }
                return "Issue does not exist.";
            }
            return "You do not have permission to perform this action.";
        }
        return "User do not exist.";
    }

    @Transactional
    public String updateIssueByStatus(String status, String issueToken, String user) {
        if(!status.equals("ACTIVE") && !status.equals("COMPLETED")) return "Invalid status.";
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent()) {
            if(issueRepository.existsByIssueToken(issueToken)) {
                issueRepository.updateIssueStatusByIssueToken(status, issueToken);
                return "Issue updated successfully.";
            }
            return "Issue does not exist.";
        }
        return "User do not exist.";
    }

    @Transactional
    public String updateIssueByStatus(String status, String issueToken, String user, String admin) {
        if(!status.equals("ACTIVE") && !status.equals("COMPLETED")) return "Invalid status.";
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (adminUser.get().getRole().equals("ADMIN")) {
                if(!issueRepository.existsByIssueToken(issueToken)) {
                    issueRepository.updateIssueStatusByIssueToken(status, issueToken);
                    return "Issue updated successfully.";
                }
                return "Issue does not exist.";
            }
            return "You do not have permission to perform this action.";
        }
        return "User do not exist.";
    }
}
