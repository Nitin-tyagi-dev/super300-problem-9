package com.cdrn.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shelters")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String address;
    private String district;
    private String state;

    @Column(nullable = false)
    private Integer totalCapacity;

    @Builder.Default
    private Integer currentOccupancy = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ShelterStatus status = ShelterStatus.OPERATIONAL;

    private Boolean hasMedicalFacility;
    private Boolean hasFood;
    private Boolean hasWater;
    private Boolean hasElectricity;
    private Boolean isAccessible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by_id")
    private User managedBy;

    private String contactNumber;
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum ShelterStatus {
        OPERATIONAL, FULL, CLOSED, DAMAGED
    }
}