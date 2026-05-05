package com.cdrn.repository;

import com.cdrn.model.DamageReport;
import com.cdrn.model.Enums;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DamageReportRepository extends MongoRepository<DamageReport, String> {
    List<DamageReport> findBySubmittedByUserId(String userId);
    List<DamageReport> findByStatus(Enums.DamageStatus status);
    List<DamageReport> findByDistrict(String district);
}
