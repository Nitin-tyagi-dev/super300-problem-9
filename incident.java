package com.cdrn.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incidents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentType type;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Severity severity = Severity.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private IncidentStatus status = IncidentStatus.REPORTED;

    // Location
    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String locationDescription;
    private String district;
    private String state;

    // Reporter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    // Assigned authority
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_authority_id")
    private User assignedAuthority;

    // Specific incident data
    private Integer affectedPersonsCount;
    private Integer injuredCount;
    private Integer missingCount;
    private String floodLevel;
    private Boolean roadBlocked;
    private Boolean requiresMedical;
    private Boolean requiresRescue;

    @ElementCollection
    @CollectionTable(name = "incident_images", joinColumns = @JoinColumn(name = "incident_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<IncidentUpdate> updates = new ArrayList<>();

    private String resolutionNotes;
    private LocalDateTime resolvedAt;

    @CreationTimestamp
    private LocalDateTime reportedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // AI/ML field
    private Double aiSeverityScore;
    private Boolean aiVerified;

    public enum IncidentType {
        FLOOD, EARTHQUAKE, CYCLONE, WILDFIRE, LANDSLIDE, TSUNAMI,
        DROUGHT, INDUSTRIAL_ACCIDENT, ROAD_ACCIDENT, FIRE, OTHER
    }

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public enum IncidentStatus {
        REPORTED, VERIFIED, RESPONDING, RESOLVED, FALSE_ALARM
    }
}