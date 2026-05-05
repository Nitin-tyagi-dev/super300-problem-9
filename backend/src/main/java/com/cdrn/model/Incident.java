package com.cdrn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "incidents")
public class Incident {
    @Id
    private String id;
    private String reportedByUserId;
    private Enums.IncidentType type;
    private Enums.Severity severity;
    private String description;
    private GeoLocation location;
    private Enums.IncidentStatus status;
    private List<String> mediaUrls;
    private int affectedPersonsCount;
    private List<String> assignedTeamIds;
    private boolean isSOS;
    private String district;
    private String state;
    private LocalDateTime reportedAt;
    private LocalDateTime resolvedAt;
    private LocalDateTime updatedAt;
}
