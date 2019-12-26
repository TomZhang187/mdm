package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccessoryRepository extends JpaRepository<Accessory, Long>, JpaSpecificationExecutor {
}
