package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Aspect.LoggingAnnotation;
import com.amdox.taskmanagement.Assests.Stage;
import com.amdox.taskmanagement.Assests.Status;
import com.amdox.taskmanagement.Repository.TaskEntity;
import com.amdox.taskmanagement.Repository.TaskRepository;
import com.amdox.taskmanagement.Repository.WorkFlowEntity;
import com.amdox.taskmanagement.Repository.WorkFlowRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkFlowInitializationService implements CommandLineRunner {

    private final WorkFlowService workFlowService;
    private final TaskRepository taskRepository;

    public WorkFlowInitializationService(WorkFlowService workFlowService, TaskRepository taskRepository) {
        this.workFlowService = workFlowService;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    @LoggingAnnotation("Workflow initialized")
    public void run(String... args) throws Exception {
        List<TaskEntity> tasks = taskRepository.findAll();
        
        for (TaskEntity task : tasks) {
            try {
                WorkFlowEntity existingWorkflow = workFlowService.getWorkflowByTaskId(task.getId());
                if (existingWorkflow != null) {
                    continue;
                }
                String stage = determineStageByStatus(task.getStatus());
                workFlowService.createWorkflow(task.getId(), stage);
                
            } catch (Exception e) {
                //omitted
            }
        }
    }

    @LoggingAnnotation("Workflow stage determined - ignorable")
    private String determineStageByStatus(Status status) {
        if (status == Status.COMPLETED) {
            return "DONE";
        } else if (status == Status.ACTIVE) {
            return "IN_PROGRESS";
        } else {
            return "TO_DO";
        }
    }
}
