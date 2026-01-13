package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Repository.LoggingEntity;
import com.amdox.taskmanagement.Repository.LoggingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LoggingService {

    private final LoggingRepository loggingRepository;

    public LoggingService(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    public List<LoggingEntity> getAllLogs() {
        return loggingRepository.findAllOrderByTimestampDesc();
    }

    public List<LoggingEntity> getLogsByUsername(String username) {
        return loggingRepository.findByUsername(username);
    }

    public List<LoggingEntity> getLogsByOrganization(String organization) {
        return loggingRepository.findByOrganization(organization);
    }

    public List<LoggingEntity> getLogsByUsernameAndOrganization(String username, String organization) {
        return loggingRepository.findByUsernameAndOrganization(username, organization);
    }

    public List<LoggingEntity> getLogsByTimestampRange(LocalDateTime startDate, LocalDateTime endDate) {
        return loggingRepository.findByTimestampBetween(startDate, endDate);
    }

    public List<LoggingEntity> getLogsByUsernameAndTimestampRange(String username, LocalDateTime startDate, LocalDateTime endDate) {
        return loggingRepository.findByUsernameAndTimestampBetween(username, startDate, endDate);
    }

    public List<LoggingEntity> getLogsByOrganizationAndTimestampRange(String organization, LocalDateTime startDate, LocalDateTime endDate) {
        return loggingRepository.findByOrganizationAndTimestampBetween(organization, startDate, endDate);
    }

    public void deleteOldLogs(LocalDateTime beforeDate) {
        loggingRepository.deleteByTimestampBefore(beforeDate);
    }

    public long getLogCount() {
        return loggingRepository.count();
    }
}

