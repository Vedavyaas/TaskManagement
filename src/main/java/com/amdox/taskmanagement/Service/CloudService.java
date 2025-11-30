package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Repository.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class CloudService{
    private final UserRepository userRepository;

    private final IssueRepository issueRepository;
    private final AttachmentRepository attachmentRepository;
    private final Cloudinary cloudinary;

    public CloudService(UserRepository userRepository, IssueRepository issueRepository, AttachmentRepository attachmentRepository, Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.attachmentRepository = attachmentRepository;
        this.cloudinary = cloudinary;
    }

    public String putAttachments(MultipartFile file, String user, String issueTitle) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        Optional<IssueEntity> issueEntity = issueRepository.findIssueEntitiesByTitle(issueTitle);
        if(userEntity.isPresent() && issueEntity.isPresent()){
            String url = createCloud(file);
            if(!url.equals("error")){
                AttachmentEntity attachmentEntity = new AttachmentEntity(file.getName(), file.getContentType(), url, userEntity.get(), issueEntity.get());
                attachmentRepository.save(attachmentEntity);
                return "Attachment saved successfully.";
            }
            return  "Attachment saved successfully.";
        }
        return "Issue does not exist.";
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
            Map uploadedFile = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("resource_type","auto")
            );
            return uploadedFile.get("secure_url").toString();
        } catch (IOException ex) {
            return "error";
        }
    }

    public ResponseEntity<String> getAttachments(String issueTitle, String user) {

    }
}
