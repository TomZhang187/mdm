package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.repository.MaterialAttributeRepository;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import com.hqhop.modules.material.service.MaterialTypeService;
import com.hqhop.modules.material.service.dto.MaterialTypeDTO;
import com.hqhop.modules.material.service.dto.MaterialTypeQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialTypeMapper;
import com.hqhop.utils.QueryHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author KinLin
* @date 2019-10-30
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialTypeServiceImpl implements MaterialTypeService {

    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    @Autowired
    private MaterialTypeMapper materialTypeMapper;

    @Autowired
    private MaterialAttributeRepository materialAttributeRepository;

    @Override
    public List<MaterialTypeDTO> queryAll(MaterialTypeQueryCriteria criteria) {
        return materialTypeMapper.toDto(materialTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));

    }

    @Override
    public Object buildTree(List<MaterialTypeDTO> typeDTOS) {
        Set<MaterialTypeDTO> trees = new LinkedHashSet<>();
        //物料分类集合
        Set<MaterialTypeDTO> materialTypes= new LinkedHashSet<>();
        //获取全部的分类名称
        List<String> deptNames = typeDTOS.stream().map(MaterialTypeDTO::getTypeName).collect(Collectors.toList());
        Boolean isChild = null;

        for (MaterialTypeDTO materialTypeDTO : typeDTOS) {
            isChild = false;
            //如果父节点id是0
            if ("0".equals(materialTypeDTO.getParentId().toString())){
                //加进树中
                trees.add(materialTypeDTO);
                System.out.println();
            }
            //
            for (MaterialTypeDTO tt : typeDTOS) {
                if (tt.getParentId().equals(materialTypeDTO.getId())) {
                    isChild = true;
                    if (materialTypeDTO.getChildren() == null) {
                        materialTypeDTO.setChildren(new ArrayList<MaterialTypeDTO>());
                        System.out.println();
                    }
                    materialTypeDTO.getChildren().add(tt);
                }
            }
            if(isChild)
                materialTypes.add(materialTypeDTO);
            else if(!deptNames.contains(materialTypeRepository.findNameById(materialTypeDTO.getParentId())))
                materialTypes.add(materialTypeDTO);
        }
        if (trees.isEmpty()) {
            trees = materialTypes;
        }

        Integer totalElements = typeDTOS!=null?typeDTOS.size():0;

        Map map = new HashMap();
        map.put("totalElements",totalElements);
        map.put("content",CollectionUtils.isEmpty(trees)?typeDTOS:trees);
        return map;
    }

    @Override
    public List<MaterialType> findByPid(Long id) {

        return materialTypeRepository.findByParentId(id);
    }

    @Override
    public MaterialType findById(Long id) {
        return materialTypeRepository.getOne(id);
    }

    /**lei
     * 新增物料小类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialType addSmallType(MaterialType materialType) {
        return materialTypeRepository.save(materialType);
    }


    /**
     * 修改物料分类
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialType update(MaterialType entity) {
        return materialTypeRepository.saveAndFlush(entity);
    }

    /**
     * 删除物料分类
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaterialType(Long id) {
        MaterialType one = materialTypeRepository.getOne(id);
        for (Attribute attribute : one.getAttributes()) {
            materialAttributeRepository.deleteByAttributeAttributeId(attribute.getAttributeId());
        }
        materialTypeRepository.deleteById(id);
    }

    @Override
    public MaterialType findByTypeName(String typeName) {
        return materialTypeRepository.findByTypeName(typeName);
    }

    @Override
    public MaterialType getOne(Long parseLong) {
        return materialTypeRepository.getOne(parseLong);
    }
}