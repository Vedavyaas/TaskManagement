package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Repository.WorkFlowEntity;
import com.amdox.taskmanagement.Service.WorkFlowService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkFlowController {

    private final WorkFlowService workFlowService;

    public WorkFlowController(WorkFlowService workFlowService) {
        this.workFlowService = workFlowService;
    }

    @GetMapping("/get/WorkFlow")
    public List<WorkFlowEntity> getWorkFlow(){
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        return workFlowService.getWorkFlowsByAdmin(admin);
    }

    @PostMapping("/create/WorkFlow")
    public WorkFlowEntity createWorkflow(@RequestParam Long taskId, @RequestParam String stage) {
        return workFlowService.createWorkflow(taskId, stage);
    }

    @PutMapping("/update/WorkFlow/{id}")
    public WorkFlowEntity updateWorkflowStage(@PathVariable Long id, @RequestParam String stage) {
        return workFlowService.updateWorkflowStage(id, stage);
    }

    @DeleteMapping("/delete/WorkFlow/{id}")
    public String deleteWorkflow(@PathVariable Long id) {
        workFlowService.deleteWorkflow(id);
        return "Workflow deleted successfully";
    }

    @GetMapping("/get/WorkFlow/{taskId}")
    public WorkFlowEntity getWorkflowByTaskId(@PathVariable Long taskId) {
        return workFlowService.getWorkflowByTaskId(taskId);
    }
}
