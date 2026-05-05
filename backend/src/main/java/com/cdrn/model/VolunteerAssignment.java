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
@Document(collection = "volunteer_assignments")
public class VolunteerAssignment {
    @Id
    private String id;
    private String volunteerId;
    private String taskId;
    private String incidentId;
    private Enums.AssignmentStatus status;
    private GeoLocation lastReportedLocation;
    private String statusUpdate;
    private LocalDateTime assignedAt;
    private LocalDateTime lastUpdatedAt;
}
