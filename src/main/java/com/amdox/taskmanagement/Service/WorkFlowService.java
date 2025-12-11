package com.amdox.taskmanagement.Service;

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
}