package com.cdrn.repository;

import com.cdrn.domain.ResourceStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceStockRepository extends JpaRepository<ResourceStock, Long> {
}
