package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.Attribute;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "attribute")
public interface AttributeService {

    @Cacheable
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
}
