package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.modules.material.service.dto.MaterialProductionDTO;
import com.hqhop.modules.material.service.dto.MaterialProductionQueryCriteria;
import com.hqhop.modules.system.domain.Dept;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;

/**
* @author wst
* @date 2019-11-26
*/
//@CacheConfig(cacheNames = "materialProduction")
public interface MaterialProductionService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(MaterialProductionQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<MaterialProductionDTO> queryAll(MaterialProductionQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    MaterialProductionDTO findById(Integer id);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    MaterialProductionDTO create(MaterialProduction resources);

    //临时保存
    @Transactional(rollbackFor = Exception.class)
    MaterialOperationRecord approvalCreate(MaterialProduction resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(MaterialProduction resources);

    /**
     * 删除
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);

    //加载当前用户可选的默认工厂集合
    @Transactional(rollbackFor = Exception.class)
    List<Dept> getUserDefaultFactory();

    //获取当前用户临时修改数据
    @Transactional(rollbackFor = Exception.class)
    MaterialProduction getTemporaryData(MaterialProduction resources);
}