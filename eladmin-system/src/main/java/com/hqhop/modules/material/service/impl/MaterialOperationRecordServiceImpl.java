package com.hqhop.modules.material.service.impl;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.material.repository.MaterialOperationRecordRepository;
import com.hqhop.modules.material.service.MaterialOperationRecordService;
import com.hqhop.modules.material.service.dto.MaterialOperationRecordDTO;
import com.hqhop.modules.material.service.dto.MaterialOperationRecordQueryCriteria;
import com.hqhop.modules.material.service.mapper.MaterialOperationRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.QueryHelp;
import java.util.List;
import java.util.Map;

/**
* @author zf
* @date 2019-12-09
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialOperationRecordServiceImpl implements MaterialOperationRecordService {

    @Autowired
    private MaterialOperationRecordRepository materialOperationRecordRepository;

    @Autowired
    private MaterialOperationRecordMapper materialOperationRecordMapper;

    @Override
    public Map<String,Object> queryAll(MaterialOperationRecordQueryCriteria criteria, Pageable pageable){
        Page<MaterialOperationRecord> page = materialOperationRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(materialOperationRecordMapper::toDto));
    }

    @Override
    public List<MaterialOperationRecordDTO> queryAll(MaterialOperationRecordQueryCriteria criteria){
        return materialOperationRecordMapper.toDto(materialOperationRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MaterialOperationRecordDTO findById(Long key) {
        Optional<MaterialOperationRecord> materialOperationRecord = materialOperationRecordRepository.findById(key);
        ValidationUtil.isNull(materialOperationRecord,"MaterialOperationRecord","key",key);
        return materialOperationRecordMapper.toDto(materialOperationRecord.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialOperationRecordDTO create(MaterialOperationRecord resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setKey(snowflake.nextId());
        return materialOperationRecordMapper.toDto(materialOperationRecordRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaterialOperationRecord resources) {
        Optional<MaterialOperationRecord> optionalMaterialOperationRecord = materialOperationRecordRepository.findById(resources.getKey());
        ValidationUtil.isNull( optionalMaterialOperationRecord,"MaterialOperationRecord","id",resources.getKey());
        MaterialOperationRecord materialOperationRecord = optionalMaterialOperationRecord.get();
        materialOperationRecord.copy(resources);
        materialOperationRecordRepository.save(materialOperationRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long key) {
        materialOperationRecordRepository.deleteById(key);
    }
}
