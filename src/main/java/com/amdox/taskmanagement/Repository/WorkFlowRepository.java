package com.amdox.taskmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkFlowRepository extends JpaRepository<WorkFlowEntity, Long> {
    WorkFlowEntity findByTask(TaskEntity task);
}
