package com.cdrn.repository;

import com.cdrn.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    long countByStatus(String status);
}
