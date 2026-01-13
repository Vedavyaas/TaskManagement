package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Repository.LoggingEntity;
import com.amdox.taskmanagement.Service.LoggingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LoggingController {

    private final LoggingService loggingService;

    public LoggingController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping
    public ResponseEntity<List<LoggingEntity>> getAllLogs() {
        List<LoggingEntity> logs = loggingService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<LoggingEntity>> getLogsByUsername(@PathVariable String username) {
        List<LoggingEntity> logs = loggingService.getLogsByUsername(username);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/organization/{organization}")
    public ResponseEntity<List<LoggingEntity>> getLogsByOrganization(@PathVariable String organization) {
        List<LoggingEntity> logs = loggingService.getLogsByOrganization(organization);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<LoggingEntity>> getLogsByFilter(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String organization,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {

        List<LoggingEntity> logs;

        if (username != null && organization != null && startDate != null && endDate != null) {
            logs = loggingService.getLogsByUsernameAndTimestampRange(username, startDate, endDate);
        } else if (username != null && organization != null) {
            logs = loggingService.getLogsByUsernameAndOrganization(username, organization);
        } else if (username != null && startDate != null && endDate != null) {
            logs = loggingService.getLogsByUsernameAndTimestampRange(username, startDate, endDate);
        } else if (organization != null && startDate != null && endDate != null) {
            logs = loggingService.getLogsByOrganizationAndTimestampRange(organization, startDate, endDate);
        } else if (startDate != null && endDate != null) {
            logs = loggingService.getLogsByTimestampRange(startDate, endDate);
        } else if (username != null) {
            logs = loggingService.getLogsByUsername(username);
        } else if (organization != null) {
            logs = loggingService.getLogsByOrganization(organization);
        } else {
            logs = loggingService.getAllLogs();
        }

        return ResponseEntity.ok(logs);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getLogCount() {
        Map<String, Long> countMap = new HashMap<>();
        countMap.put("totalLogs", loggingService.getLogCount());
        return ResponseEntity.ok(countMap);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteOldLogs(@RequestParam LocalDateTime beforeDate) {
        loggingService.deleteOldLogs(beforeDate);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logs deleted successfully");
        response.put("deletedBefore", beforeDate.toString());
        return ResponseEntity.ok(response);
    }
}

