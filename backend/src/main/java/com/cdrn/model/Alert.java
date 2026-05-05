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
@Document(collection = "alerts")
public class Alert {
    @Id
    private String id;
    private String issuedByAuthorityId;
    private Enums.AlertType type;
    private Enums.DisasterType disasterType;
    private String title;
    private String message;
    private Enums.AlertSeverity severity;
    private List<String> targetDistricts;
    private List<String> targetStates;
    private GeoLocation epicenter;
    private double radiusKm;
    private boolean isBroadcast;
    private boolean smsSent;
    private boolean pushSent;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
