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
@Document(collection = "otp_records")
public class OtpRecord {
    @Id
    private String id;
    private String phone;
    private String otp;
    private boolean used;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
