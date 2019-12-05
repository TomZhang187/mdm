package com.hqhop.modules.system.service;

import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.service.dto.DeptDTO;
import com.hqhop.modules.system.service.dto.DeptQueryCriteria;
import com.taobao.api.ApiException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@CacheConfig(cacheNames = "dept")
public interface DeptService {

    /**
     * queryAll
     * @param criteria
     * @return
     */
    @Cacheable
    List<DeptDTO> queryAll(DeptQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DeptDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    Dept create(Dept resources) throws
            ApiException ;

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Dept resources)throws
            ApiException ;

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id)throws
            ApiException ;

    /**
     * buildTree
     * @param deptDTOS
     * @return
     */
    @Cacheable
    Object buildTree(List<DeptDTO> deptDTOS);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable
    List<Dept> findByPid(long pid);

    Set<Dept> findByRoleIds(Long id);
}
