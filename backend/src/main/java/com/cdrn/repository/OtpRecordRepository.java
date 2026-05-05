package com.cdrn.repository;

import com.cdrn.model.OtpRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OtpRecordRepository extends MongoRepository<OtpRecord, String> {
    Optional<OtpRecord> findTopByPhoneOrderByCreatedAtDesc(String phone);
}
