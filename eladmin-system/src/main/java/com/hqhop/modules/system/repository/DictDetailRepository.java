package com.hqhop.modules.system.repository;

import com.hqhop.modules.system.domain.DictDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictDetailRepository extends JpaRepository<DictDetail, Long>, JpaSpecificationExecutor {


    @Transactional
    @Query(value = "select label,value from  sys_dict_detail where dict_id =?1", nativeQuery = true)
    List<DictDetail> findAllByDictId(Long dictId);

   DictDetail findByValueAndDict_Id(String value,Long dictId);

    DictDetail findByLabelAndDict_Id(String label,Long dictId);
}
