package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Entity.Attachment;
import com.amdox.taskmanagement.Service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/{issueId}")
    public ResponseEntity<Attachment> uploadAttachment(@PathVariable Long issueId,
                                                       @RequestParam("file") MultipartFile file,
                                                       @RequestParam("uploadedBy") String uploadedBy) {
        return ResponseEntity.ok(attachmentService.uploadAttachment(issueId, file, uploadedBy));
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Attachment>> getAttachments(@PathVariable Long issueId) {
        return ResponseEntity.ok(attachmentService.getAttachmentsForIssue(issueId));
    }
}
