package com.hqhop.modules.employee.repository;

import com.hqhop.modules.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author chengy
* @date 2019-10-21
*/
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor {
    /**
     * findByEmployeeCardId
     * @param employee_card_id
     * @return
     */
    Employee findByEmployeeCardId(String employee_card_id);

    /**
     * findByEmployeeCode
     * @param employee_code
     * @return
     */
    Employee findByEmployeeCode(String employee_code);

    /**
     * findByEmployeePhone
     * @param employee_phone
     * @return
     */
    Employee findByEmployeePhone(String employee_phone);

    /**
     * findByEmployeeState
     * @param employee_state
     * @return
     */
    Employee findByEmployeeState(Integer employee_state);
}