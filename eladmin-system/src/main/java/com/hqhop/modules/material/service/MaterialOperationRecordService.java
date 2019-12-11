package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.service.dto.MaterialOperationRecordDTO;
import com.hqhop.modules.material.service.dto.MaterialOperationRecordQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @author zf
* @date 2019-12-09
*/
//@CacheConfig(cacheNames = "materialOperationRecord")
public interface MaterialOperationRecordService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(MaterialOperationRecordQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<MaterialOperationRecordDTO> queryAll(MaterialOperationRecordQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param key
     * @return
     */
    //@Cacheable(key = "#p0")
    MaterialOperationRecordDTO findById(Long key);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    MaterialOperationRecordDTO create(MaterialOperationRecord resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(MaterialOperationRecord resources);

    /**
     * 删除
     * @param key
     */
    //@CacheEvict(allEntries = true)
    void delete(Long key);
}