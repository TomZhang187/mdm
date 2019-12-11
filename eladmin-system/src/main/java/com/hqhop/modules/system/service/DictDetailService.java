package com.hqhop.modules.system.service;

import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.service.dto.DictDetailDTO;
import com.hqhop.modules.system.service.dto.DictDetailQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@CacheConfig(cacheNames = "dictDetail")
public interface DictDetailService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DictDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DictDetailDTO create(DictDetail resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(DictDetail resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    @Cacheable
    Map queryAll(DictDetailQueryCriteria criteria, Pageable pageable);

    /*
        返回label-value Map集合对象
        * */
    @Transactional(rollbackFor = Exception.class)
    Map getLabelByValue(DictDetailQueryCriteria criteria);

    /*
        返回value-label Map集合
        * */
@Transactional(rollbackFor = Exception.class)
Map getValueByLabel(DictDetailQueryCriteria criteria);

    /*
          拿到字典标签通过字典值
        * */
    @Transactional(rollbackFor = Exception.class)
    String getDicLabel(String dictName, Integer value);

    /*
   拿到字典标签通过字典值
   * */
    @Transactional(rollbackFor = Exception.class)
    String getDicLabel(String dictName, String value);

    /*
            拿到字典值通过字典标签
           * */
    @Transactional(rollbackFor = Exception.class)
    String getDicValue(String dictName,  String label);
}
