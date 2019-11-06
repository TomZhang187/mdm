package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;


    @Override
    public List<Attribute> queryAllByMaterialType(Long typeId) {
        if(typeId==null){
            return null;
        }
        return attributeRepository.findByMaterialTypeId(typeId);
    }
}
