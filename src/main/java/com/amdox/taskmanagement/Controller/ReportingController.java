package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    @Autowired
    private ReportingService reportingService;

    @GetMapping("/burnDown/{sprintId}")
    public ResponseEntity<Map<String, Object>> burnDown(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.burndown(sprintId));
    }

    @GetMapping("/velocity/{projectId}")
    public ResponseEntity<Map<String, Object>> velocity(@PathVariable Long projectId) {
        return ResponseEntity.ok(reportingService.velocity(projectId));
    }

    @GetMapping("/sprintReport/{sprintId}")
    public ResponseEntity<Map<String, Object>> sprintReport(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.sprintRepot(sprintId));
    }

    @GetMapping("/epicReport/{epicId}")
    public ResponseEntity<Map<String, Object>> epicReport(@PathVariable Long epicId) {
        return ResponseEntity.ok(reportingService.epicReport(epicId));
    }

    @GetMapping("/workLoadReport/{sprintId}")
    public ResponseEntity<Map<String, Object>> workLoadReport(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.workLoad(sprintId));
    }

    @GetMapping("/cumultaive-flow/{sprintId}")
    public ResponseEntity<Map<String, Object>> cumultaiveFlow(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.cumulativeFlow(sprintId));
    }
}
