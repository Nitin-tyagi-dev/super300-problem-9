package com.cdrn.repository;

import com.cdrn.domain.SosRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SosRequestRepository extends JpaRepository<SosRequest, Long> {
    long countByStatus(String status);
}
