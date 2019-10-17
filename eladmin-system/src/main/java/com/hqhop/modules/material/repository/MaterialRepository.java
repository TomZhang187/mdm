package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author chengy
* @date 2019-10-17
*/
public interface MaterialRepository extends JpaRepository<Material, Integer>, JpaSpecificationExecutor {
}