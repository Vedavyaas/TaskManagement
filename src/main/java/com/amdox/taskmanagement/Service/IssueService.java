package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Assests.IssueDTO;
import com.amdox.taskmanagement.Assests.IssueEnrollment;
import com.amdox.taskmanagement.Repository.IssueEntity;
import com.amdox.taskmanagement.Repository.IssueRepository;
import com.amdox.taskmanagement.Repository.UserEntity;
import com.amdox.taskmanagement.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public List<IssueDTO> getIssuesByUserName(String userName, String admin) {
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> user = userRepository.findByUsername(userName);

        if (adminUser.isPresent() && user.isPresent()) {
            if ("ADMIN".equals(adminUser.get().getRole())) {
                List<IssueEntity> issues = issueRepository.findIssueEntitiesByIssuedUser(user.get());
                return issues.stream().map(issue -> new IssueDTO(issue.getTitle(), issue.getDescription(), issue.getComments(), admin,  userName, issue.getStatus())).collect(Collectors.toList());
            }
            return null;
        }
        return null;
    }

    public String createIssue(IssueEnrollment issueEnrollment, String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);
        Optional<UserEntity> assignedToUser = userRepository.findByUsername(issueEnrollment.issuedTo());
        if (assignedToUser.isPresent() && user.isPresent()) {
            IssueEntity issueEntity = new IssueEntity(issueEnrollment.title(), issueEnrollment.description(), issueEnrollment.comment(), user.get(), assignedToUser.get());
            issueRepository.save(issueEntity);
            return "Issue created successfully.";
        }
        return "User do not exist.";
    }

    public String updateIssue(IssueEnrollment issueEnrollment, String userName) {
        Optional<UserEntity> currentUserOpt = userRepository.findByUsername(userName);
        Optional<UserEntity> issuedByUserOpt = userRepository.findByUsername(issueEnrollment.issuedBy());
        Optional<UserEntity> issuedToUserOpt = userRepository.findByUsername(issueEnrollment.issuedTo());

        if (currentUserOpt.isEmpty()) {
            return "Authenticated user does not exist.";
        }

        if (issuedByUserOpt.isEmpty()) {
            return "Issued By user does not exist.";
        }

        Optional<IssueEntity> issueOpt = issueRepository.findIssueEntitiesByIssuedUserAndTitle(issuedByUserOpt.get(), issueEnrollment.title());
        if (issueOpt.isEmpty()) {
            return "Cannot find issue with title: " + issueEnrollment.title() + ".";
        }

        IssueEntity issue = issueOpt.get();
        UserEntity currentUser = currentUserOpt.get();

        boolean isIssuedBy = issue.getIssuedUser().getUsername().equals(currentUser.getUsername());
        boolean isIssuedTo = issue.getIssuedTo() != null && issue.getIssuedTo().getUsername().equals(currentUser.getUsername());

        if (!(isIssuedBy || isIssuedTo)) {
            return "You do not have permission to update this issue.";
        }
        issue.setDescription(issueEnrollment.description());
        issue.setComments(issueEnrollment.comment());
        if (issuedToUserOpt.isPresent()) {
            UserEntity issuedToUser = issuedToUserOpt.get();
            if (issue.getIssuedTo() == null || !issue.getIssuedTo().getUsername().equals(issuedToUser.getUsername())) {
                issue.setIssuedTo(issuedToUser);
            }
        }
        issueRepository.save(issue);
        return "Issue updated successfully.";
    }

    public String closeIssue(IssueEnrollment issueEnrollment, String userName) {
        if (issueRepository.existsByTitle(issueEnrollment.title())) {
            Optional<UserEntity> user = userRepository.findByUsername(userName);
            if (user.isPresent() && "ADMIN".equals(user.get().getRole())) {
                issueRepository.deleteIssueEntitiesByTitle(issueEnrollment.title());
                return "Issue closed successfully.";
            }
            return "You don't have permission to close this issue.";
        }
        return "Issue does not exist.";
    }
}
