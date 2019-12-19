package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.modules.material.repository.MaterialOperationRecordRepository;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.hqhop.utils.SecurityUtils;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.material.repository.MaterialProductionRepository;
import com.hqhop.modules.material.service.MaterialProductionService;
import com.hqhop.modules.material.service.dto.MaterialProductionDTO;
import com.hqhop.modules.material.service.dto.MaterialProductionQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialProductionMapper;
import javafx.scene.DepthTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import java.util.List;
import java.util.Map;

/**
* @author wst
* @date 2019-11-26
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialProductionServiceImpl implements MaterialProductionService {

    @Autowired
    private MaterialProductionRepository materialProductionRepository;

    @Autowired
    private MaterialProductionMapper materialProductionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MaterialOperationRecordRepository materialOperationRecordRepository;









    @Override
    public Map<String,Object> queryAll(MaterialProductionQueryCriteria criteria, Pageable pageable){

        if(criteria.getEnable() ==null){
            criteria.setEnable(true);
        }
        Page<MaterialProduction> page = materialProductionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(materialProductionMapper::toDto));
    }

    @Override
    public List<MaterialProductionDTO> queryAll(MaterialProductionQueryCriteria criteria){
        return materialProductionMapper.toDto(materialProductionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MaterialProductionDTO findById(Integer id) {
        Optional<MaterialProduction> materialProduction = materialProductionRepository.findById(id);
        ValidationUtil.isNull(materialProduction,"MaterialProduction","id",id);
        return materialProductionMapper.toDto(materialProduction.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialProductionDTO create(MaterialProduction resources) {
        resources.setEnable(true);
        resources.setApprovalState("1");
        resources.setProductionSalesman(SecurityUtils.getEmployeeName());
        return materialProductionMapper.toDto(materialProductionRepository.save(resources));
    }


    //临时保存
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialOperationRecord approvalCreate(MaterialProduction resources) {
        //7临时保存 ...更多看字典
        MaterialOperationRecord record1 = materialOperationRecordRepository.findByIdAndCreatorAndOperationType(resources.getId().longValue(),SecurityUtils.getUsername(),"7");
          MaterialOperationRecord record2 =null;
      if(record1 == null){
          MaterialOperationRecord record = new MaterialOperationRecord();
          record.getDataByMateriaProduction(resources);
          //7临时保存 ...更多看字典
          record.setOperationType("7");
          record.setCreator(SecurityUtils.getUsername());
          record.setUserId(SecurityUtils.getDingId());
          record.setId(resources.getId().longValue());
          record.setMaterialId(resources.getMaterial().getId());
          record2 = materialOperationRecordRepository.save(record);
      }else {
          record1.getDataByMateriaProduction(resources);
          record2 = materialOperationRecordRepository.save(record1);
      }
          return  record2;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaterialProduction resources) {

        if("4".equals(resources.getApprovalState())){
            approvalCreate(resources);
        }else {
            Optional<MaterialProduction> optionalMaterialProduction = materialProductionRepository.findById(resources.getId());
            ValidationUtil.isNull( optionalMaterialProduction,"MaterialProduction","id",resources.getId());
            MaterialProduction materialProduction = optionalMaterialProduction.get();
            materialProduction.copy(resources);
            materialProductionRepository.save(materialProduction);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        materialProductionRepository.deleteById(id);
    }



    //加载当前用户可选的默认工厂集合
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Dept> getUserDefaultFactory() {
        UserDTO userDTO = userService.findByName(SecurityUtils.getUsername());
       List<Dept> list = new ArrayList<>(userDTO.getBelongFiliales());

       return list;
    }



    //获取当前用户临时修改数据
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialProduction getTemporaryData(MaterialProduction resources){

        //7临时保存 ...更多看字典
        MaterialOperationRecord record = materialOperationRecordRepository.findByIdAndCreatorAndOperationType(resources.getId().longValue(), SecurityUtils.getUsername(), "7");
        if(record != null){
            MaterialProduction materialProduction = record.getMaterialProduction();
            Material material = new Material();
            material.setId(record.getMaterialId());
            materialProduction.setMaterial(material);
            return  materialProduction;
        }
        return  null;
    }

}