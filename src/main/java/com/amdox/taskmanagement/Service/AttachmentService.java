package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Entity.Attachment;
import com.amdox.taskmanagement.Repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepo;

    @Autowired
    private CloudService cloudService; // Existing CloudService using Cloudinary

    public Attachment uploadAttachment(Long issueId, MultipartFile file, String uploadedBy) {
        try {
            Map uploadResult = cloudService.upload(file);
            
            Attachment attachment = new Attachment();
            attachment.setIssueId(issueId);
            attachment.setFileName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setFileSize(file.getSize());
            attachment.setUploadedBy(uploadedBy);
            
            // Map Cloudinary result
            attachment.setStoragePath((String) uploadResult.get("url"));
            attachment.setCloudId((String) uploadResult.get("public_id"));
            
            return attachmentRepo.save(attachment);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload attachment", e);
        }
    }

    public List<Attachment> getAttachmentsForIssue(Long issueId) {
        return attachmentRepo.findByIssueId(issueId);
    }
}
