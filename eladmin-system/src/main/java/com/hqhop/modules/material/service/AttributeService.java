package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.service.vo.AttributeVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "attribute")
public interface AttributeService {


    List<Attribute> queryAllByMaterialType(Long typeId);

    @Cacheable
    List<Attribute> findList(Long[] typeId);

    /**
     * 新增属性
     * @param attribute
     * @return
     */
    @Cacheable
    Attribute addAttribute(Attribute attribute);

    /**
     * 删除指定分类的属性
     * @param id
     * @param attributeId
     */
    void attributeDelete(Long id, Long attributeId);

    List<Attribute> findAll();

    void deleteOne(Long id);

    void updateAttribute(AttributeVo attributeVo);

    List<Attribute> queryAllByMaterialId(Long id);

    Attribute findAttributeByAttributeName(String attributeName);

    Attribute getOne(Long attributeId);

    Attribute findAttributeByAttributeIdAndMaterialsTypeId(Long attributeId, Long typeId);

    void setTypeAttribute(Long attributeId, Long typeId);
}
