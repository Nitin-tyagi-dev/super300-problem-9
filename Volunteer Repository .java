package com.cdrn.repository;

import com.cdrn.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    long countByAvailableTrue();
}
