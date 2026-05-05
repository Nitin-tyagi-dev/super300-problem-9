package com.cdrn.repository;

import com.cdrn.model.Alert;
import com.cdrn.model.Enums;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {
    //added new alerts repo
    List<Alert> findByTargetDistrictsContaining(String district);
    List<Alert> findBySeverityAndExpiresAtAfter(Enums.AlertSeverity severity, LocalDateTime now);
    @Query("{ 'expiresAt': { $gt: ?0 } }")
    List<Alert> findActiveAlerts(LocalDateTime now);
}
