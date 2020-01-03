package com.hqhop.modules.company.repository;

import com.hqhop.modules.company.domain.EmployeeCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author zf
* @date 2020-01-02
*/
public interface EmployeeCompanyRepository extends JpaRepository<EmployeeCompany, Long>, JpaSpecificationExecutor {


    List<EmployeeCompany> findByEmployeeKey(Long key);

   List<EmployeeCompany> findByCompanyKey(Long key);



    /**
     * 自定义删除中间表需要打的注解
     */
    @Transactional
    @Modifying
    @Query(value = "delete from  employee_company  where employee_key=?1 and company_key=?2", nativeQuery = true)
    void deleteEmployeeCompany(Long employeeKey, Long coompanyKey);


    EmployeeCompany findByCompanyKeyAndEmployeeKey(Long companyKey,Long employeeKey);


}