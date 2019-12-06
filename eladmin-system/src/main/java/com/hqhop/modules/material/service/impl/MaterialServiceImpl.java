package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Attribute;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialAttribute;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.repository.AttributeRepository;
import com.hqhop.modules.material.repository.MaterialAttributeRepository;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.repository.MaterialTypeRepository;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.dto.MaterialDTO;
import com.hqhop.modules.material.service.dto.MaterialQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialMapper;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.SecurityUtils;
import com.hqhop.utils.ValidationUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author KinLin
 * @date 2019-10-30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private MaterialAttributeRepository materialAttributeRepository;
    @Autowired
    private MaterialTypeRepository materialTypeRepository;
    @Autowired
    private AttributeRepository attributeRepository;


    @Override
    public Map<String, Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable) {
        Page<Material> page = materialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(materialMapper::toDto));
    }

    @Override
    public List<MaterialDTO> queryAll(MaterialQueryCriteria criteria) {
        return materialMapper.toDto(materialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Material findById(Long id) {
        Optional<Material> material = materialRepository.findById(id);
        ValidationUtil.isNull(material, "Material", "id", id);

        return material.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Material create(Material resources) {
        if (resources == null) {
            return null;
        }
        //防止类型不存在
       /* MaterialType byId = materialTypeRepository.getOne(resources.getType().getId());
        resources.setType(byId);*/
       //查找上一个的获取流水码
        Material material = materialRepository.lastTimeMaterial(resources.getType().getParentId());
        Material save = materialRepository.save(resources);
        Set<Attribute> attributes = resources.getAttributes();
        MaterialType one = materialTypeRepository.getOne(save.getType().getId());
        List<Attribute> collect = attributes.stream().collect(Collectors.toList());
        String model = save.getModel();
        String[] split = model.split("，");
        if(split.length!=attributes.size()){
            return null;
        }
        for (Attribute attribute : collect) {
            Attribute one1 = attributeRepository.getOne(attribute.getAttributeId());
            attribute.setAttributeNumber(one1.getAttributeNumber());
        }
        Collections.sort(collect);
        for (int j = 0, len = collect.size(); j < len; j++) {
            collect.get(j).setAttributeValue(split[j]);
        }
        //设置编号
        Integer countByTypeId=null;
        if (material==null){
           countByTypeId=1;
        }else{
            String substring = material.getRemark().substring(4);
            long l = Long.parseLong(substring)+1;
            countByTypeId= Math.toIntExact(l);
        }
        Long id = save.getType().getId();
        MaterialType one1 = materialTypeRepository.getOne(id);
        Long parentId = one1.getParentId();
        DecimalFormat df = new DecimalFormat("000");
        String str2 = df.format(parentId);
        df = new DecimalFormat("00000");
        String str3 = df.format(countByTypeId);
        save.setRemark(str2 + "." + str3);
        //设置当前操作用户的用户名
        String username = SecurityUtils.getUsername();
        save.setCreatePerson(username);
        collect.forEach(attribute -> materialAttributeRepository.save(new MaterialAttribute(save, attribute, attribute.getAttributeValue())));
        return save;
    }

    /**
     * @param materialEntity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Material materialEntity) {
        Optional<Material> materialById = materialRepository.findById(materialEntity.getId());
        Set<Attribute> attributes = materialEntity.getAttributes();
        MaterialType one = materialTypeRepository.getOne(materialEntity.getType().getId());
        List<Attribute> collect = attributes.stream().collect(Collectors.toList());
        attributes.forEach(attribute -> materialAttributeRepository.updateByAttributeId(attribute.getAttributeValue(), attribute.getAttributeId(), materialEntity.getId()));
        ValidationUtil.isNull(materialById, "Material", "id", materialEntity.getId());
        Material material1 = materialById.get();
        materialRepository.save(materialEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (null == id) {
            System.out.println("id不正确");
        }
        materialAttributeRepository.deleteByMaterialId(id);
        materialRepository.deleteById(id);
    }

    /**
     * 通过类型id查找该类型物料
     *
     * @param typeId
     * @return
     */
    @Override
    public List<Material> queryAllByType(Long typeId, Integer pageNo, Integer pageSize) {
        return materialRepository.findAllByType(typeId, pageNo, pageSize);
    }

    /**
     * 通过类型id查找该类型物料数量
     *
     * @param typeId
     * @return
     */
    @Override
    public Integer getCountByTypeId(Long typeId) {

        return materialRepository.getCountByTypeId(typeId);
    }

    /**
     * @param typePid
     * @return
     */
    @Override
    public List<Material> queryAllByTyPid(Long typePid) {
        return null;
    }

    /**
     * 查询二级目录
     *
     * @param typeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public List<Material> findAllBySecondaryType(Long typeId, Integer pageNo, Integer pageSize) {
        return materialRepository.findAllBySecondaryType(typeId, pageNo, pageSize);
    }

    @Override
    public Integer getCountBySecondaryType(Long typePid) {
        return materialRepository.getCountBySecondaryType(typePid);
    }

    /**
     * 查询最顶级分类
     *
     * @param typeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public List<Material> findAllByTopType(Long typeId, Integer pageNo, Integer pageSize) {
        return materialRepository.findAllByTopType(typeId, pageNo, pageSize);
    }

    @Override
    public Integer getCountByTopType(Long typePid) {
        return materialRepository.getCountByTopType(typePid);
    }

    @Override
    public List<Attribute> getMaterialAttributes(Long materialTypeId) {
        return materialRepository.getMaterialAttributes(materialTypeId);
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public List<Material> queryAllByTypeId(Long id) {
        return materialRepository.queryAllByTypeId(id);
    }

    @Override
    public Material findByNameAndModel(String name, String model) {
        return materialRepository.findByNameAndModel(name,model);
    }


}