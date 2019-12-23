package com.hqhop.modules.material.service.impl;

import com.hqhop.exception.BadRequestException;
import com.hqhop.modules.material.domain.*;
import com.hqhop.modules.material.repository.*;
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

import java.sql.Timestamp;
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

    @Autowired
    private AttributeServiceImpl attributeService;

    @Autowired
    private MaterialOperationRecordRepository materialOperationRecordRepository;

    @Override
    public Map<String, Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable) {
        criteria.setEnable("true");
        Page<Material> page = materialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(materialMapper::toDto));
    }

    @Override
    public List<MaterialDTO> queryAll(MaterialQueryCriteria criteria) {
        criteria.setEnable("true");
        return materialMapper.toDto(materialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Material findById(Long id) {
        Optional<Material> material = materialRepository.findById(id);
        ValidationUtil.isNull(material, "Material", "id", id);
        Material material1 = material.get();
        List<Attribute> attributes = attributeService.queryAllByMaterialId(material1.getId());
        material1.setAttributes(attributes.stream().collect(Collectors.toSet()));
        return material1;
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
        MaterialType one2 = materialTypeRepository.getOne(resources.getType().getId());
        Material material = materialRepository.lastTimeMaterial(one2.getParentId());
        Material save = materialRepository.save(resources);
        Set<Attribute> attributes = resources.getAttributes();
        MaterialType one = materialTypeRepository.getOne(save.getType().getId());
        List<Attribute> collect = attributes.stream().collect(Collectors.toList());
        String model = save.getModel();
        String[] split = model.split("，");
        if (split.length != attributes.size()) {
            throw new BadRequestException("型号不对");
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
        Integer countByTypeId = null;
        if (material == null) {
            countByTypeId = 1;
        } else {
            String substring = material.getRemark().substring(4);
            long l = Long.parseLong(substring) + 1;
            countByTypeId = Math.toIntExact(l);
        }
        Long id = save.getType().getId();
        MaterialType one1 = materialTypeRepository.getOne(id);
        Long parentId = one1.getParentId();
        DecimalFormat df = new DecimalFormat("000");
        String str2 = df.format(parentId);
        df = new DecimalFormat("00000");
        String str3 = df.format(countByTypeId);
        save.setRemark(str2 + "." + str3);
        //1新建....更多对照字典
        if (save.getApprovalState() == null) {
            save.setApprovalState("1");
        }
        //设置当前操作用户的用户名
        String username = SecurityUtils.getEmployeeName();
        save.setCreatePerson(username);
        save.setCreateTime(new Timestamp(new Date().getTime()));
        collect.forEach(attribute -> materialAttributeRepository.save(new MaterialAttribute(save, attribute, attribute.getAttributeValue())));
        return save;
    }

    /**
     * @param materialEntity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Material update(Material materialEntity) {
        Optional<Material> materialById = materialRepository.findById(materialEntity.getId());
        Set<Attribute> attributes = materialEntity.getAttributes();
        //MaterialType one = materialTypeRepository.getOne(materialEntity.getType().getId());
        List<Attribute> collect = attributes.stream().collect(Collectors.toList());
        attributes.forEach(attribute -> materialAttributeRepository.updateByAttributeId(attribute.getAttributeValue(), attribute.getAttributeId(), materialEntity.getId()));
        ValidationUtil.isNull(materialById, "Material", "id", materialEntity.getId());
        Material material1 = materialById.get();
        Material material = materialRepository.save(materialEntity);

        return material;
    }


    /**
     * 审批通过数据，修改临时保存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Material ApprovalUpdate(Material material) {


        if (material.getEnable()) {
            MaterialOperationRecord record = new MaterialOperationRecord();
            record.setId(material.getId());
            material.setId(null);
            material.setEnable(false);
            Material material1 = create(material);
            record.setTemporaryId(material1.getId());
            record.setCreatePerson(SecurityUtils.getUsername());
            //7临时保存 .....物料操作字典
            record.setOperationType("7");
            materialOperationRecordRepository.save(record);
            materialRepository.save(material);

            return material;
        } else {
            Material material1 = update(material);
            return material1;
        }


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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaterial(Material material) {
        if (null == material.getId()) {
            System.out.println("id不正确");
        }
        materialRepository.deleteById(material.getId());
        materialAttributeRepository.deleteByMaterialId(material.getId());
        Set<Attribute> attributes = material.getAttributes();
        for (Attribute attribute : attributes) {
            attributeRepository.deleteById(attribute.getAttributeId());
        }


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
        return materialRepository.findByNameAndModel(name, model);
    }


    //查询是否有当前用户临时保存的数据
    @Override
    public Material findTemporaryData(Material resources) {
        //7临时保存 .....物料操作类型字典
        MaterialOperationRecord record = materialOperationRecordRepository.findByIdAndCreatorAndOperationType(resources.getId(), SecurityUtils.getUsername(), "7");
        if (record != null) {
            Material material1 = materialRepository.findByKey(record.getTemporaryId());
            if (material1 != null) {
                return material1;
            }
        }
        return null;
    }
    //获取流水码
    @Override
    public String getWaterCode(Long typeId) {
        MaterialType one2 = materialTypeRepository.getOne(typeId);
        Material material = materialRepository.lastTimeMaterial(one2.getParentId());
        Integer countByTypeId = null;
        if (material == null) {
            countByTypeId = 1;
        } else {
            String substring = material.getRemark().substring(4);
            long l = Long.parseLong(substring) + 1;
            countByTypeId = Math.toIntExact(l);
        }
        Long parentId = one2.getParentId();
        MaterialType materialType = materialTypeRepository.getOne(parentId);
        DecimalFormat df = new DecimalFormat("00000");
        String str3 = df.format(countByTypeId);
        return materialType.getMaterialTypeCode() + "." + str3;
    }

}