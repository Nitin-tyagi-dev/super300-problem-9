package com.cdrn.repository;

import com.cdrn.model.Enums;
import com.cdrn.model.Incident;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IncidentRepository extends MongoRepository<Incident, String> {
    List<Incident> findByStatusAndDistrict(Enums.IncidentStatus status, String district);
    List<Incident> findByStatus(Enums.IncidentStatus status);
    List<Incident> findByIsSOSTrue();
    List<Incident> findByTypeAndStatus(Enums.IncidentType type, Enums.IncidentStatus status);
    List<Incident> findByDistrict(String district, Pageable pageable);
    long countByTypeAndStatus(Enums.IncidentType type, Enums.IncidentStatus status);
}
