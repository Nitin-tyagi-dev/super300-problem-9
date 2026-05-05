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
@Document(collection = "damage_reports")
public class DamageReport {
    @Id
    private String id;
    private String submittedByUserId;
    private String incidentId;
    private Enums.DamageCategory category;
    private String description;
    private double estimatedLoss;
    private String currency;
    private List<String> mediaUrls;
    private GeoLocation location;
    private Enums.DamageStatus status;
    private String reviewedByAuthorityId;
    private String reviewNotes;
    private boolean compensationClaimed;
    private String district;
    private LocalDateTime submittedAt;
}
