package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialAttribute;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.repository.MaterialAttributeRepository;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import com.hqhop.modules.material.service.AttributeService;
import com.hqhop.modules.material.service.vo.AttributeVo;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private MaterialAttributeRepository materialAttributeRepository;

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
    @Transactional(rollbackFor = Exception.class)
    public Attribute addAttribute(Attribute attribute) {
        String name = attribute.getAttributeName();
        //先判断该属性是否已经存在
        Attribute existAttribute = attributeRepository.findAttributesByAttributeName(name);
        if(existAttribute!=null){
            return null;
        }
        //先保存关联对象,再保存关系
        Set<MaterialType> materialTypes = attribute.getMaterialTypes();
        attribute.setMaterialTypes(null);
        Attribute save = attributeRepository.save(attribute);
        save.setMaterialTypes(materialTypes);
        Attribute save1 = attributeRepository.save(save);
        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void attributeDelete(Long id, Long attributeId) {
        attributeRepository.attributeDelete(id,attributeId);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(Long id) {
        attributeRepository.deleteById(id);
        materialAttributeRepository.deleteByAttributeAttributeId(id);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttribute(AttributeVo attributeVo) {
        attributeRepository.updateAttribute(attributeVo.getAttributeName(),attributeVo.getAttributeId());
    }


    @Override
    public List<Attribute> queryAllByMaterialId(Long id) {
        List<Attribute> attributes = attributeRepository.queryAllByMaterialId(id);
        for (Attribute attribute : attributes) {
            MaterialAttribute byAttributeId = materialAttributeRepository.findByAttributeId(attribute.getAttributeId(), id);
            attribute.setAttributeValue(byAttributeId.getAttributeValue());
        }
        System.out.println(attributes);
        return attributes;
    }

    @Override
    public Attribute findAttributeByAttributeName(String attributeName) {
        return attributeRepository.findAttributeByAttributeName(attributeName);
    }

    @Override
    public Attribute getOne(Long attributeId) {
        return attributeRepository.getOne(attributeId);
    }

    @Override
    public Attribute findAttributeByAttributeIdAndMaterialsTypeId(Long attributeId, Long typeId) {
        return attributeRepository.findAttributeByAttributeIdAndMaterialsTypeId(attributeId,typeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setTypeAttribute(Long attributeId, Long typeId) {
        attributeRepository.setTypeAttribute(attributeId,typeId);
    }


}
