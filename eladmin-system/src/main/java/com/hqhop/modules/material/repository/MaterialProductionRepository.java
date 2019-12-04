package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.MaterialProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author wst
* @date 2019-11-26
*/
public interface MaterialProductionRepository extends JpaRepository<MaterialProduction, Integer>, JpaSpecificationExecutor {
}