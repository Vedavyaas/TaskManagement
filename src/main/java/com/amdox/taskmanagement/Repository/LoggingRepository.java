package com.amdox.taskmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoggingRepository extends JpaRepository<LoggingEntity, Long> {

    List<LoggingEntity> findByUsername(String username);

    List<LoggingEntity> findByOrganization(String organization);

    List<LoggingEntity> findByUsernameAndOrganization(String username, String organization);

    @Query("SELECT l FROM LoggingEntity l WHERE l.timestamp BETWEEN :startDate AND :endDate ORDER BY l.timestamp DESC")
    List<LoggingEntity> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT l FROM LoggingEntity l WHERE l.username = :username AND l.timestamp BETWEEN :startDate AND :endDate ORDER BY l.timestamp DESC")
    List<LoggingEntity> findByUsernameAndTimestampBetween(
            @Param("username") String username,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT l FROM LoggingEntity l WHERE l.organization = :organization AND l.timestamp BETWEEN :startDate AND :endDate ORDER BY l.timestamp DESC")
    List<LoggingEntity> findByOrganizationAndTimestampBetween(
            @Param("organization") String organization,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Modifying
    @Query("DELETE FROM LoggingEntity l WHERE l.timestamp < :beforeDate")
    void deleteByTimestampBefore(@Param("beforeDate") LocalDateTime beforeDate);

    @Query("SELECT l FROM LoggingEntity l ORDER BY l.timestamp DESC")
    List<LoggingEntity> findAllOrderByTimestampDesc();
}
