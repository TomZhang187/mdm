package com.hqhop.modules.company.service;

import com.hqhop.modules.company.domain.EmployeeCompany;
import com.hqhop.modules.company.service.dto.EmployeeCompanyDTO;
import com.hqhop.modules.company.service.dto.EmployeeCompanyQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;

/**
* @author zf
* @date 2020-01-02
*/
//@CacheConfig(cacheNames = "employeeCompany")
public interface EmployeeCompanyService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(EmployeeCompanyQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<EmployeeCompanyDTO> queryAll(EmployeeCompanyQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param companyKey
     * @return
     */
    //@Cacheable(key = "#p0")
    EmployeeCompanyDTO findById(Long companyKey);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    EmployeeCompanyDTO create(EmployeeCompany resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(EmployeeCompany resources);

    /**
     * 删除
     * @param companyKey
     */
    //@CacheEvict(allEntries = true)
    void delete(Long companyKey);

    //保存 通过员工id和客商id
     @Transactional(rollbackFor = Exception.class)
     void saveBykey(Long employeeKey, Long coompanyKey);

    //删除 通过员工id和客商id
    @Transactional(rollbackFor = Exception.class)
    void deleteBykey(Long employeeKey, Long coompanyKey);
}