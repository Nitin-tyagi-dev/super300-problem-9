package com.cdrn.repository;

import com.cdrn.model.Enums;
import com.cdrn.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
    List<User> findByRoleAndDistrict(Enums.Role role, String district);
    List<User> findByRoleAndIsVerified(Enums.Role role, boolean verified);
}
