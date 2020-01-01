package com.hqhop.modules.company.service.impl;

import com.hqhop.modules.company.domain.CompanyBasic;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.company.repository.CompanyBasicRepository;
import com.hqhop.modules.company.service.CompanyBasicService;
import com.hqhop.modules.company.service.dto.CompanyBasicDTO;
import com.hqhop.modules.company.service.dto.CompanyBasicQueryCriteria;
import com.hqhop.modules.company.service.mapper.CompanyBasicMapper;
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
* @author lyl
* @date 2020-01-01
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyBasicServiceImpl implements CompanyBasicService {

    @Autowired
    private CompanyBasicRepository companyBasicRepository;

    @Autowired
    private CompanyBasicMapper companyBasicMapper;

    @Override
    public Map<String,Object> queryAll(CompanyBasicQueryCriteria criteria, Pageable pageable){
        Page<CompanyBasic> page = companyBasicRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(companyBasicMapper::toDto));
    }

    @Override
    public List<CompanyBasicDTO> queryAll(CompanyBasicQueryCriteria criteria){
        return companyBasicMapper.toDto(companyBasicRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public CompanyBasicDTO findById(Long key) {
        Optional<CompanyBasic> companyBasic = companyBasicRepository.findById(key);
        ValidationUtil.isNull(companyBasic,"CompanyBasic","key",key);
        return companyBasicMapper.toDto(companyBasic.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyBasicDTO create(CompanyBasic resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setKey(snowflake.nextId());
        return companyBasicMapper.toDto(companyBasicRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CompanyBasic resources) {
        Optional<CompanyBasic> optionalCompanyBasic = companyBasicRepository.findById(resources.getKey());
        ValidationUtil.isNull( optionalCompanyBasic,"CompanyBasic","id",resources.getKey());
        CompanyBasic companyBasic = optionalCompanyBasic.get();
        companyBasic.copy(resources);
        companyBasicRepository.save(companyBasic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long key) {
        companyBasicRepository.deleteById(key);
    }
}
