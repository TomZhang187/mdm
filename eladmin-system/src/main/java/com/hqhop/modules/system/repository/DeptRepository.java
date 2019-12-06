package com.hqhop.modules.system.repository;

import com.hqhop.modules.system.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor {

    /**
     * findByPid
     * @param id
     * @return
     */
    List<Dept> findByPid(Long id);

    @Query(value = "select * from sys_dept where pid = ?1",nativeQuery = true)
    Dept findByParentId(Long id);

    @Query(value = "select name from sys_dept where id = ?1",nativeQuery = true)
    String findNameById(Long id);

    Set<Dept> findByRoles_Id(Long id);

    Dept findByDingId(String dingId);
    @Query(value = "select * from sys_dept where id = ?1",nativeQuery = true)
   Dept findByKey(Long key);


}
