package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Repository.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CloudService{

    @Value("${CLOUDINARY_URL}")
    String cloudUrl;
    private final UserRepository userRepository;

    private final IssueRepository issueRepository;
    private final AttachmentRepository attachmentRepository;

    public CloudService(UserRepository userRepository, IssueRepository issueRepository, AttachmentRepository attachmentRepository) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public String putAttachments(MultipartFile file, String user, String issueTitle) {
        if (file == null || file.isEmpty()) return "Invalid file.";
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        Optional<IssueEntity> issueEntity = issueRepository.findIssueEntitiesByTitle(issueTitle);
        if(userEntity.isPresent() && issueEntity.isPresent()){
            String url = createCloud(file);
            if(!url.equals("error")){
                AttachmentEntity attachmentEntity = new AttachmentEntity(file.getName(), file.getContentType(), url, userEntity.get(), issueEntity.get());
                attachmentRepository.save(attachmentEntity);
                return "Attachment saved successfully.";
            }
            return "Upload failed.";
        }
        return "Issue or user does not exist.";
    }

    public String putAttachments(MultipartFile file, String user,  String issueTitle, String admin) {
        Optional<UserEntity> adminEntity = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        Optional<IssueEntity> issueEntity = issueRepository.findIssueEntitiesByTitle(issueTitle);
        if(userEntity.isPresent()){
            if(adminEntity.isPresent() && issueEntity.isPresent()){
                if(adminEntity.get().getRole().equals("ADMIN")){
                    String url = createCloud(file);
                    if(!url.equals("error")){
                        AttachmentEntity attachmentEntity = new AttachmentEntity(file.getName(), file.getContentType(), url, userEntity.get(), issueEntity.get());
                        attachmentRepository.save(attachmentEntity);
                        return "Attachment saved successfully.";
                    }
                    return "Upload failed.";
                }
                return "You dont have enough permission to upload this file.";
            }
            return "Issue does not exist.";
        }
        return "User does not exist.";
    }
    private String createCloud(MultipartFile file){
        try {
            Cloudinary cloudinary = new Cloudinary(cloudUrl);
            Map uploadedFile = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("resource_type","auto")
            );
            return uploadedFile.get("secure_url").toString();
        } catch (IOException ex) {
            return "error";
        }
    }

    public String getAttachments(String issueTitle, String user) {
        Optional<IssueEntity> issueEntity = issueRepository.findIssueEntitiesByTitle(issueTitle);
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (issueEntity.isEmpty()) return "Issue does not exist.";
        if (userEntity.isEmpty()) return "User does not exist.";

        List<AttachmentEntity> attachments = attachmentRepository.findByIssue(issueEntity.get());
        if (attachments.isEmpty()) return "No attachments found.";
        StringBuilder result = new StringBuilder();
        for (AttachmentEntity a : attachments) {
            result.append(a.getFileUrl()).append("\n");
        }
        return result.toString();
    }

    public String getAttachments(String issueTitle, String user, String admin) {
        Optional<IssueEntity> issueEntity = issueRepository.findIssueEntitiesByTitle(issueTitle);
        Optional<UserEntity> adminEntity = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (issueEntity.isEmpty()) return "Issue does not exist.";
        if (adminEntity.isEmpty()) return "Admin does not exist.";
        if (userEntity.isEmpty()) return "User does not exist.";
        if (!adminEntity.get().getRole().equals("ADMIN")) return "You dont have enough permission to perform this action.";
        List<AttachmentEntity> attachments = attachmentRepository.findByIssue(issueEntity.get());
        if (attachments.isEmpty()) return "No attachments found.";
        StringBuilder result = new StringBuilder();
        for (AttachmentEntity a : attachments) {
            result.append(a.getFileUrl()).append("\n");
        }
        return result.toString();
    }
}
