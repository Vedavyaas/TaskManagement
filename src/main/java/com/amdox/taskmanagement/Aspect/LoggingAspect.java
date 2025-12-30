package com.amdox.taskmanagement.Aspect;

import com.amdox.taskmanagement.Repository.LoggingEntity;
import com.amdox.taskmanagement.Repository.LoggingRepository;
import com.amdox.taskmanagement.Repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final LoggingRepository loggingRepository;

    private final UserRepository userRepository;

    public LoggingAspect(LoggingRepository loggingRepository, UserRepository userRepository) {
        this.loggingRepository = loggingRepository;
        this.userRepository = userRepository;
    }

    @Before("@annotation(loggingAnnotation)")
    public void logging(LoggingAnnotation loggingAnnotation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Skip logging if no authenticated user (e.g., during startup)
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getPrincipal().equals("anonymousUser")) {
            return;
        }
        
        String user = authentication.getName();
        String organization = userRepository.findOrganizationByUsername(user);
        String message = loggingAnnotation.value();
        loggingRepository.save(new LoggingEntity(user, organization, message));
    }
}
