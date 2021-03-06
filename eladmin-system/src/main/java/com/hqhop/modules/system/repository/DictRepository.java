package com.hqhop.modules.system.repository;

import com.hqhop.modules.system.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor {

    //通过过名字拿字典
    Dict findByName(String name);

}
