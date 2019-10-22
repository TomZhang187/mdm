package com.hqhop.modules.employee.service;

import com.hqhop.modules.employee.domain.Employee;
import com.hqhop.modules.employee.service.dto.EmployeeDTO;
import com.hqhop.modules.employee.service.dto.EmployeeQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.List;

/**
* @author chengy
* @date 2019-10-21
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
    List<EmployeeDTO> queryAll(EmployeeQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    EmployeeDTO findById(Long id);

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
     * 删除
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 导出数据
     * @param queryAll
     * @param response
     * @throws IOException
     */
    void download(List<EmployeeDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 根据工号查找数据
     * @param employeeCode
     * @return
     */
    EmployeeDTO findByEmployeeCode(String employeeCode);
}