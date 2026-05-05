package com.cdrn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String incidentId;
    private String assignedToUserId;
    private String assignedByAuthorityId;
    private Enums.TaskType type;
    private String title;
    private String description;
    private Enums.TaskStatus status;
    private GeoLocation targetLocation;
    private LocalDateTime deadline;
    private int priority;
    private String notes;
    private LocalDateTime assignedAt;
    private LocalDateTime completedAt;
}
