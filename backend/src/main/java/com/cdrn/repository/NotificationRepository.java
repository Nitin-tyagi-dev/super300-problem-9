package com.cdrn.repository;

import com.cdrn.model.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserIdAndIsReadFalse(String userId);
    List<Notification> findByUserId(String userId, Pageable pageable);
}
