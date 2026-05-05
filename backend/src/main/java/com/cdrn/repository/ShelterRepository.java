package com.cdrn.repository;

import com.cdrn.model.Shelter;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShelterRepository extends MongoRepository<Shelter, String> {
    List<Shelter> findByIsActiveAndDistrict(boolean active, String district);
    List<Shelter> findByLocationNear(Point point, Distance distance);
    //shelter repository
}
