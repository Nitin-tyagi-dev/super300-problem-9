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
@Document(collection = "resources")
public class Resource {
    @Id
    private String id;
    private Enums.ResourceType type;
    private String name;
    private int quantity;
    private int availableQuantity;
    private String unit;
    private String locationId;
    private String managedByUserId;
    private String district;
    private Enums.ResourceStatus status;
    private LocalDateTime updatedAt;
}
