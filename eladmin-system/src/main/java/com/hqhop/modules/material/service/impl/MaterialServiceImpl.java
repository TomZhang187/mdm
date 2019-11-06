package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.service.MaterialService;
import com.hqhop.modules.material.service.dto.MaterialDTO;
import com.hqhop.modules.material.service.dto.MaterialQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialMapper;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import com.hqhop.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Material findById(Long id) {
        Optional<Material> material = materialRepository.findById(id);
        ValidationUtil.isNull(material,"Material","id",id);

        return material.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Material create(Material resources) {
        if(resources==null){
            return null;
        }
        return materialRepository.save(resources);
    }

    @Override
    public void update(Material materialEntity) {
        Optional<Material> materialById = materialRepository.findById(materialEntity.getId());
        ValidationUtil.isNull( materialById,"Material","id", materialEntity.getId());
        Material material1 = materialById.get();
        materialRepository.save(material1);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if(null==id){
            System.out.println("id不正确");
        }
        materialRepository.deleteById(id);
    }

    @Override
    public List<Material> queryAllByType(Long typeId) {

        return materialRepository.findAllByType(typeId);
    }

    @Override
    public List<Material> queryAllByTyPid(Long typePid) {
        return null;
    }


}