package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Assests.Stage;
import com.amdox.taskmanagement.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkFlowService {

    private final WorkFlowRepository workFlowRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public WorkFlowService(WorkFlowRepository workFlowRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.workFlowRepository = workFlowRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<WorkFlowEntity> getWorkFlowsByAdmin(String adminUsername) {
        Optional<UserEntity> adminEntity = userRepository.findByUsername(adminUsername);

        if (adminEntity.isEmpty()) {
            return List.of();
        }

        if (!"admin".equalsIgnoreCase(adminEntity.get().getRole())) {
            return List.of();
        }

        List<TaskEntity> tasks = taskRepository.findTaskEntityByAssignedUser(adminEntity.get());

        return tasks.stream()
                .map(workFlowRepository::findByTask)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    public WorkFlowEntity createWorkflow(Long taskId, String stageString) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        
        if (taskOptional.isEmpty()) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        // Check if workflow already exists for this task
        WorkFlowEntity existingWorkflow = workFlowRepository.findByTask(taskOptional.get());
        if (existingWorkflow != null) {
            throw new RuntimeException("Workflow already exists for task id: " + taskId);
        }

        try {
            Stage stage = Stage.valueOf(stageString.toUpperCase());
            WorkFlowEntity workflow = new WorkFlowEntity(taskOptional.get(), stage);
            return workFlowRepository.save(workflow);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid stage: " + stageString + ". Valid stages are: TO_DO, IN_PROGRESS, DONE");
        }
    }

    public WorkFlowEntity updateWorkflowStage(Long workflowId, String stageString) {
        Optional<WorkFlowEntity> workflowOptional = workFlowRepository.findById(workflowId);
        
        if (workflowOptional.isEmpty()) {
            throw new RuntimeException("Workflow not found with id: " + workflowId);
        }

        try {
            Stage stage = Stage.valueOf(stageString.toUpperCase());
            WorkFlowEntity workflow = workflowOptional.get();
            workflow.setStage(stage);
            return workFlowRepository.save(workflow);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid stage: " + stageString + ". Valid stages are: TO_DO, IN_PROGRESS, DONE");
        }
    }

    public void deleteWorkflow(Long workflowId) {
        if (!workFlowRepository.existsById(workflowId)) {
            throw new RuntimeException("Workflow not found with id: " + workflowId);
        }
        workFlowRepository.deleteById(workflowId);
    }

    public WorkFlowEntity getWorkflowByTaskId(Long taskId) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        
        if (taskOptional.isEmpty()) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        return workFlowRepository.findByTask(taskOptional.get());
    }
}
