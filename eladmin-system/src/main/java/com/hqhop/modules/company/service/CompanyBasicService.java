package com.hqhop.modules.company.service;

import com.hqhop.modules.company.domain.CompanyBasic;
import com.hqhop.modules.company.service.dto.CompanyBasicDTO;
import com.hqhop.modules.company.service.dto.CompanyBasicQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @author lyl
* @date 2020-01-01
*/
//@CacheConfig(cacheNames = "companyBasic")
public interface CompanyBasicService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(CompanyBasicQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<CompanyBasicDTO> queryAll(CompanyBasicQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param key
     * @return
     */
    //@Cacheable(key = "#p0")
    CompanyBasicDTO findById(Long key);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    CompanyBasicDTO create(CompanyBasic resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(CompanyBasic resources);

    /**
     * 删除
     * @param key
     */
    //@CacheEvict(allEntries = true)
    void delete(Long key);
}