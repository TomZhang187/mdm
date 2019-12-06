package com.hqhop.modules.system.repository;

import com.hqhop.modules.system.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zf
* @date 2019-11-28
*/
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor {

    //通过钉钉id查找员工
    Employee findByDingId(String dingId);


    //通过员工编码查询员工
    Employee findByEmployeeCode(String code);




}