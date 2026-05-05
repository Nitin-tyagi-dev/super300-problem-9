package com.cdrn.repository;

import com.cdrn.domain.VolunteerTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VolunteerTaskRepository extends JpaRepository<VolunteerTask, Long> {
    List<VolunteerTask> findByStatusOrderByUpdatedAtDesc(String status);
}
