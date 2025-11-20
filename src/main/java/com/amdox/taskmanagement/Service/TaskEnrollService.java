package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Assests.Status;
import com.amdox.taskmanagement.Assests.TaskDisplayer;
import com.amdox.taskmanagement.Assests.TaskEnrollment;
import com.amdox.taskmanagement.Repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskEnrollService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public TaskEnrollService(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String createTask(TaskEnrollment taskEnrollment) {
        if (taskRepository.existsByTitle(taskEnrollment.title())) return "Title already exists";
        Optional<UserEntity> userEntity = userRepository.findByEmail(taskEnrollment.createdUserEmail());
        Optional<UserEntity> userEntityAssgined = userRepository.findByEmail(taskEnrollment.assignedUserEmail());
        if (userEntity.isPresent() && userEntityAssgined.isPresent()) {
            if (userRepository.findRoleByEmail(userEntity.get().getEmail()).equals("ADMIN")) {
                UserEntity admin = userEntity.get();
                UserEntity user = userEntityAssgined.get();
                TaskEntity taskEntity = new TaskEntity(taskEnrollment.title(), taskEnrollment.description(),
                        Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now(), null,
                        taskEnrollment.dueDate(), taskEnrollment.priority(), admin, user);
                taskRepository.save(taskEntity);
                return "Task created successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return userEntity.isPresent() ? "Assigned user email not found." : "Created user email not found.";
    }

    @Transactional
    public String modifyTask(TaskEnrollment taskEnrollment) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(taskEnrollment.createdUserEmail());
        Optional<UserEntity> userEntityAssgined = userRepository.findByEmail(taskEnrollment.assignedUserEmail());

        if (userEntity.isPresent() && userEntityAssgined.isPresent()) {
            if (userRepository.findRoleByEmail(userEntity.get().getEmail()).equals("ADMIN")) {
                UserEntity admin = userEntity.get();
                UserEntity user = userEntityAssgined.get();
                Optional<TaskEntity> task = taskRepository.getTaskEntityByTitleAndUser(taskEnrollment.title(), admin);
                if (!task.isPresent()) {
                    return "Task not found.";
                }
                TaskEntity taskEntity = task.get();
                taskEntity.setTitle(taskEnrollment.title());
                taskEntity.setDescription(taskEnrollment.description());
                taskEntity.setStatus(Status.ACTIVE);
                taskEntity.setUpdatedAt(LocalDateTime.now());
                taskEntity.setDueDate(taskEnrollment.dueDate());
                taskEntity.setPriority(taskEnrollment.priority());
                taskEntity.setCreatedBy(admin);
                taskEntity.setAssignedUser(user);
                taskRepository.save(taskEntity);
                return "Changed successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return userEntity.isPresent() ? "Assigned user email not found." : "Created user email not found.";
    }

    public String deleteTask(TaskEnrollment taskEnrollment) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(taskEnrollment.createdUserEmail());
        Optional<UserEntity> userEntityAssgined = userRepository.findByEmail(taskEnrollment.assignedUserEmail());
        if (userEntity.isPresent() && userEntityAssgined.isPresent()) {
            if (userRepository.findRoleByEmail(userEntity.get().getEmail()).equals("ADMIN")) {
                UserEntity admin = userEntity.get();
                UserEntity user = userEntityAssgined.get();

                taskRepository.removeTaskEntitiesByTitleAndAssignedUserAndCreatedBy(taskEnrollment.title(), user, admin);
                return "Task deleted successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return userEntity.isPresent() ? "Email id not found." : "User id not found.";
    }

    public List<TaskDisplayer> getAllTasks(String email, String password, String userEmail) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        Optional<UserEntity> userEntityAssgined = userRepository.findByEmail(userEmail);

        if (userEntity.isPresent() && userEntityAssgined.isPresent()) {
            UserEntity admin = userEntity.get();
            UserEntity user = userEntityAssgined.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {
                List<TaskEntity> tasks = taskRepository.findTaskEntityByAssignedUserAndCreatedBy(user, admin);
                List<TaskDisplayer> taskDisplayers = new ArrayList<>();
                for (var i : tasks) {
                    taskDisplayers.add(new TaskDisplayer(i.getTitle(), i.getDescription(), i.getStatus(), i.getCreatedAt(), i.getUpdatedAt(), i.getCompletedAt(), i.getDueDate(), i.getPriority(), i.getAssignedUser().getEmail(), i.getCreatedBy().getEmail()));
                }
                return taskDisplayers;
            }
            return null;
        }
        return null;
    }

    @Transactional
    public String setTaskCompleted(TaskEnrollment taskEnrollment) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(taskEnrollment.createdUserEmail());
        Optional<UserEntity> userEntityAssgined = userRepository.findByEmail(taskEnrollment.assignedUserEmail());

        if (userEntity.isPresent() && userEntityAssgined.isPresent()) {
            if (userRepository.findRoleByEmail(userEntity.get().getEmail()).equals("ADMIN") ||
                    userEntity.get().getEmail().equals(taskEnrollment.assignedUserEmail())) {
                Optional<TaskEntity> task = taskRepository.getTaskEntityByTitleAndUser(taskEnrollment.title(), userEntity.get());
                task.ifPresent(taskEntity -> {
                    taskEntity.setStatus(Status.COMPLETED);
                    taskRepository.save(taskEntity);
                });
                return "Task completed successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return userEntity.isPresent() ? "Assigned user email not found." : "Created user email not found.";
    }
}
