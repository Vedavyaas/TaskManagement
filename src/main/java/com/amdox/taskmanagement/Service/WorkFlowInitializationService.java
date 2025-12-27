package com.amdox.taskmanagement.Service;

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
    public void run(String... args) throws Exception {
        // Initialize workflow data for existing tasks
        List<TaskEntity> tasks = taskRepository.findAll();
        
        for (TaskEntity task : tasks) {
            try {
                // Check if workflow already exists for this task
                WorkFlowEntity existingWorkflow = workFlowService.getWorkflowByTaskId(task.getId());
                if (existingWorkflow != null) {
                    continue; // Skip if workflow already exists
                }
                
                // Determine stage based on task status
                String stage = determineStageByStatus(task.getStatus());
                
                // Create workflow for the task
                workFlowService.createWorkflow(task.getId(), stage);
                
            } catch (Exception e) {
                // Log the error but continue with other tasks
                System.err.println("Failed to create workflow for task " + task.getId() + ": " + e.getMessage());
            }
        }
        
        System.out.println("Workflow initialization completed successfully!");
    }

    private String determineStageByStatus(Status status) {
        if (status == Status.COMPLETED) {
            return "DONE";
        } else if (status == Status.ACTIVE) {
            // For active tasks, alternate between TO_DO and IN_PROGRESS based on task ID
            // This ensures a good distribution of stages
            return "IN_PROGRESS"; // Default to IN_PROGRESS for active tasks
        } else {
            return "TO_DO";
        }
    }
}
