package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Assests.Stage;
import jakarta.persistence.*;

@Entity
public class WorkFlowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private TaskEntity task;

    @Enumerated(EnumType.STRING)
    private Stage stage;

    public WorkFlowEntity() {}

    public WorkFlowEntity(TaskEntity task, Stage stage) {
        this.task = task;
        this.stage = stage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
