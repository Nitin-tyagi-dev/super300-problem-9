package com.cdrn.repository;

import com.cdrn.model.Enums;
import com.cdrn.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByAssignedToUserId(String userId);
    List<Task> findByIncidentId(String incidentId);
    List<Task> findByStatus(Enums.TaskStatus status);
}
