package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.dto.MaterialTypeDTO;
import com.hqhop.modules.material.service.dto.MaterialTypeQueryCriteria;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
* @author KinLin
* @date 2019-10-30
*/
@CacheConfig(cacheNames = "materialType")
public interface MaterialTypeService {

    /**
     * queryAll 查询所有的分类
     * @return
     */
    @Cacheable
    List<MaterialTypeDTO> queryAll(MaterialTypeQueryCriteria criteria);

    /**
     * buildTree 快速建树
     * @param typeDTOS
     * @return
     */
    @Cacheable
    Object buildTree(List<MaterialTypeDTO> typeDTOS);

    /**
     * findByPId 查找某个父级下的所有子集
     * @param id
     * @return
     */
    @Cacheable
    List<MaterialType> findByPid(Long id);

    /**
     * findById
     * @param id
     * @return
     */
    MaterialType findById(Long id);

    MaterialType addSmallType(MaterialType materialType);

    MaterialType addBigType(MaterialType materialType);


    public MaterialType update(MaterialType entity);
}