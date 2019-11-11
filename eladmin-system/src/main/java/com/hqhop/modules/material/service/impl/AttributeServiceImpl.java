package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.service.AttributeService;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    /**
     * 通过物料类型id查找所有该类型物料的属性
     * @param typeId
     * @return
     */
    @Override
    public List<Attribute> queryAllByMaterialType(Long typeId) {
        if(typeId==null){
            return null;
        }
        return attributeRepository.findByMaterialTypeId(typeId);
    }

    /**
     *  通过多个属性id查找属性
     * @param attributeId
     * @return
     */
    @Override
    public List<Attribute> findList(Long[] attributeId) {
        List<Long> idList = Arrays.asList(attributeId);
        List<Attribute> list= attributeRepository.findAllById(idList);
        return null;
    }

    /**
     * 新增物料属性
     * @param attribute
     * @return
     */
    @Override
    public Attribute addAttribute(Attribute attribute) {
        String name = attribute.getAttributeName();
        String value = attribute.getAttributeValue();
        //先判断该属性是否已经存在
        Attribute existAttribute = attributeRepository.findAttributesByAttributeNameAndAttributeValue(name, value);
        if(existAttribute!=null){
            return null;
        }
        Attribute save = attributeRepository.save(attribute);
        return save;
    }
}
