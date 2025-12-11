# TODO: Fix CloudService Errors

## Steps to Complete
- [x] Update AttachmentRepository.java: Change findByIssue to return List<AttachmentEntity>
- [x] Update CloudService.java:
  - [x] Add validation for MultipartFile (null/empty checks) in putAttachments methods
  - [x] Fix error message in putAttachments (3 params) to return "Upload failed." on upload failure
  - [x] Correct permission check in getAttachments (3 params) to deny if user is not ADMIN
  - [x] Modify getAttachments methods to return all attachment URLs or "No attachments found." if none
- [x] Compile the project to check for errors
- [x] Run basic tests if possible (tests failed due to unrelated bean definition issue in CloudConfig, not due to our fixes)
