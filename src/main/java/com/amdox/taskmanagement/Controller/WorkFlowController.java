package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Repository.WorkFlowEntity;
import com.amdox.taskmanagement.Service.WorkFlowService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
