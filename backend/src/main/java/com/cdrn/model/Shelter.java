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
@Document(collection = "shelters")
public class Shelter {
    @Id
    private String id;
    private String name;
    private Enums.ShelterType type;
    private GeoLocation location;
    private int totalCapacity;
    private int currentOccupancy;
    private boolean isActive;
    private boolean hasFood;
    private boolean hasMedical;
    private boolean hasWater;
    private String contactPhone;
    private String managedByUserId;
    private String district;
    private List<String> resourceIds;
    private LocalDateTime createdAt;
}
