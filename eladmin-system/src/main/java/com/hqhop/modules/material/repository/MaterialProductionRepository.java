package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.MaterialProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author wst
* @date 2019-11-26
*/
public interface MaterialProductionRepository extends JpaRepository<MaterialProduction, Integer>, JpaSpecificationExecutor {

        @Query(value="select * from material_production where id=?1", nativeQuery = true)
         MaterialProduction findByKey(Integer id);



        MaterialProduction findByOriginalRemark(String remark);
}