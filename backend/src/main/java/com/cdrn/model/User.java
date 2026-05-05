package com.cdrn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String phone;
    @Indexed(unique = true)
    private String email;
    private String passwordHash;
    private Enums.Role role;
    private String aadhaarNumber;
    private boolean isVerified;
    private boolean isActive;
    private GeoLocation lastLocation;
    private String fcmToken;
    private List<String> assignedTaskIds;
    private List<String> skillTags;
    private String district;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
