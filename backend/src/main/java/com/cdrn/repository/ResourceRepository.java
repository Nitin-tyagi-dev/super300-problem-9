package com.cdrn.repository;

import com.cdrn.model.Enums;
import com.cdrn.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findByTypeAndDistrict(Enums.ResourceType type, String district);
    List<Resource> findByStatus(Enums.ResourceStatus status);
}
