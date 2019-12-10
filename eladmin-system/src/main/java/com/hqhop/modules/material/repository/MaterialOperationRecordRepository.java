package com.hqhop.modules.material.repository;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zf
* @date 2019-12-09
*/
public interface MaterialOperationRecordRepository extends JpaRepository<MaterialOperationRecord, Long>, JpaSpecificationExecutor {
}