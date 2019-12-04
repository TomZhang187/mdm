package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.material.repository.MaterialProductionRepository;
import com.hqhop.modules.material.service.MaterialProductionService;
import com.hqhop.modules.material.service.dto.MaterialProductionDTO;
import com.hqhop.modules.material.service.dto.MaterialProductionQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialProductionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public Map<String,Object> queryAll(MaterialProductionQueryCriteria criteria, Pageable pageable){
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
        return materialProductionMapper.toDto(materialProductionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaterialProduction resources) {
        Optional<MaterialProduction> optionalMaterialProduction = materialProductionRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMaterialProduction,"MaterialProduction","id",resources.getId());
        MaterialProduction materialProduction = optionalMaterialProduction.get();
        materialProduction.copy(resources);
        materialProductionRepository.save(materialProduction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        materialProductionRepository.deleteById(id);
    }
}
