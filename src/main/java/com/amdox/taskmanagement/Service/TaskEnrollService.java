package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Assests.Status;
import com.amdox.taskmanagement.Assests.TaskDTO;
import com.amdox.taskmanagement.Assests.TaskEnrollment;
import com.amdox.taskmanagement.Repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskEnrollService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskEnrollService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAllTasks(String admin, String username) {
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (adminUser.get().getRole().equals("ADMIN")) {
                List<TaskEntity> tasks = taskRepository.findTaskEntityByAssignedUserAndCreatedBy(adminUser.get(), userEntity.get());
                return tasks.stream().map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt(), task.getCompletedAt(), task.getDueDate(), task.getPriority(), task.getCreatedBy().getUsername(), task.getAssignedUser().getUsername())).toList();
            }
        }
        return null;
    }

    public List<TaskDTO> getAllTasks(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            List<TaskEntity> tasks = taskRepository.findTaskEntityByAssignedUser(userEntity.get());
            return tasks.stream().map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt(), task.getCompletedAt(), task.getDueDate(), task.getPriority(), task.getCreatedBy().getUsername(), task.getAssignedUser().getUsername())).toList();
        }
        return null;
    }

    public String createTask(TaskEnrollment taskEnrollment, String admin) {
        if (taskRepository.existsByTitle(taskEnrollment.title())) return "Title already exists";
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(taskEnrollment.username());
        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (adminUser.get().getRole().equals("ADMIN")) {
                TaskEntity taskEntity = new TaskEntity(taskEnrollment.title(), taskEnrollment.description(),
                        Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now(), null,
                        taskEnrollment.dueDate(), taskEnrollment.priority(), userEntity.get(), adminUser.get());
                taskRepository.save(taskEntity);
                return "Task created successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return "User not found.";
    }

    public String deleteTask(String title, String admin) {
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        if (!taskRepository.existsByTitle(title)) return "Task not found.";
        if (adminUser.isPresent()) {
            if (adminUser.get().getRole().equals("ADMIN")) {
                taskRepository.removeTaskEntitiesByTitleAndCreatedBy(title, adminUser.get());
                return "Task deleted successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return "User not found.";
    }


    public String updateTaskDescription(String title, String description, String user) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            taskRepository.updateDescriptionAndUpdatedAtByTitleAndUser(description, LocalDateTime.now(), title, userEntity.get());
            return "Task updated successfully!";
        }
        return "User not found.";
    }

    public String updateTaskDescription(String title, String description, String user, String admin) {
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);

        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            if (adminUser.get().getRole().equals("ADMIN")) {
                taskRepository.updateDescriptionAndUpdatedAtByTitleAndUser(description, LocalDateTime.now(), title, userEntity.get());
                return "Task updated successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return "User not found.";
    }

    public String updateTaskStatus(String title, String status, String user) {
        if(!status.equals("ACTIVE") && !status.equals("COMPLETED")) return "Invalid status";
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            taskRepository.updateStatusAndCompletedAtByTitleAndUser(status, LocalDateTime.now(), title, userEntity.get());
            return "Task updated successfully!";
        }
        return "User not found.";
    }

    public String updateTaskStatus(String title, String status, String user, String admin) {
        if(!status.equals("ACTIVE") && !status.equals("COMPLETED")) return "Invalid status";
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            if (adminUser.get().getRole().equals("ADMIN")) {
                taskRepository.updateStatusAndCompletedAtByTitleAndUser(status, LocalDateTime.now(), title, userEntity.get());
                return "Task updated successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return "User not found.";
    }

    public String updateDueDate(LocalDateTime dueDate, String title, String user) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            taskRepository.updateDueDateAndUpdatedAtByTitleAndUser(dueDate, LocalDateTime.now(), title, userEntity.get());
            return "Task updated successfully!";
        }
        return "User not found.";
    }

    public String updateDueDate(LocalDateTime dueDate, String title, String user, String admin) {
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);

        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            if (adminUser.get().getRole().equals("ADMIN")) {
                taskRepository.updateDueDateAndUpdatedAtByTitleAndUser(dueDate, LocalDateTime.now(), title, userEntity.get());
                return "Task updated successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return "User not found.";
    }

    public String updatePriority(String title, String priority, String user) {
        if(!priority.equals("ACTIVE") && !priority.equals("COMPLETED")) return "Invalid priority";
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            taskRepository.updatePriorityAndUpdatedAtByTitleAndUser(priority, LocalDateTime.now(), title, userEntity.get());
            return "Task updated successfully!";
        }
        return "User not found.";
    }

    public String updatePriority(String title, String priority, String user, String admin) {
        if(!priority.equals("HIGH") && !priority.equals("MEDIUM") && !priority.equals("LOW")) return "Invalid priority";
        Optional<UserEntity> adminUser = userRepository.findByUsername(admin);
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);
        if (userEntity.isPresent() && adminUser.isPresent()) {
            if (!taskRepository.existsByTitleAndUser(title, userEntity.get())) return "Task not found.";
            if (adminUser.get().getRole().equals("ADMIN")) {
                taskRepository.updatePriorityAndUpdatedAtByTitleAndUser(priority, LocalDateTime.now(), title, userEntity.get());
                return "Task updated successfully!";
            }
            return "You dont have permission to perform this action.";
        }
        return "User not found.";
    }
}
