package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Material;
import me.zhengjie.utils.ValidationUtil;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.dto.MaterialDTO;
import com.hqhop.modules.material.service.dto.MaterialQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;

/**
* @author chengy
* @date 2019-10-17
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public Map<String,Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable){
        Page<Material> page = materialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(materialMapper::toDto));
    }

    @Override
    public List<MaterialDTO> queryAll(MaterialQueryCriteria criteria){
        return materialMapper.toDto(materialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MaterialDTO findById(Integer id) {
        Optional<Material> material = materialRepository.findById(id);
        ValidationUtil.isNull(material,"Material","id",id);
        return materialMapper.toDto(material.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialDTO create(Material resources) {
        return materialMapper.toDto(materialRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Material resources) {
        Optional<Material> optionalMaterial = materialRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMaterial,"Material","id",resources.getId());
        Material material = optionalMaterial.get();
        material.copy(resources);
        materialRepository.save(material);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        materialRepository.deleteById(id);
    }
}