package com.hqhop.modules.system.service;

import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.service.dto.EmployeeDTO;
import com.hqhop.modules.system.service.dto.EmployeeQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @author zf
* @date 2019-11-28
*/
//@CacheConfig(cacheNames = "employee")
public interface EmployeeService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(EmployeeQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<Employee> queryAll(EmployeeQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    EmployeeDTO findById(Long id);

    //通过工号查询
    Employee getEmployeeByCode(String code);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    EmployeeDTO create(Employee resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Employee resources);

    /**
     * 拿到字符串部门列表
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    String getDeptsStr(String depts);
}