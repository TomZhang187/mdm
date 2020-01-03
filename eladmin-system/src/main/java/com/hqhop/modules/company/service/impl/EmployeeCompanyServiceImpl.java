package com.hqhop.modules.company.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.hqhop.modules.company.domain.EmployeeCompany;
import com.hqhop.modules.company.repository.EmployeeCompanyRepository;
import com.hqhop.modules.company.service.EmployeeCompanyService;
import com.hqhop.modules.company.service.dto.EmployeeCompanyDTO;
import com.hqhop.modules.company.service.dto.EmployeeCompanyQueryCriteria;
import com.hqhop.modules.company.service.mapper.EmployeeCompanyMapper;
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
* @author zf
* @date 2020-01-02
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmployeeCompanyServiceImpl implements EmployeeCompanyService {

    @Autowired
    private EmployeeCompanyRepository employeeCompanyRepository;

    @Autowired
    private EmployeeCompanyMapper employeeCompanyMapper;

    @Override
    public Map<String,Object> queryAll(EmployeeCompanyQueryCriteria criteria, Pageable pageable){
        Page<EmployeeCompany> page = employeeCompanyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(employeeCompanyMapper::toDto));
    }

    @Override
    public List<EmployeeCompanyDTO> queryAll(EmployeeCompanyQueryCriteria criteria){
        return employeeCompanyMapper.toDto(employeeCompanyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public EmployeeCompanyDTO findById(Long companyKey) {
        Optional<EmployeeCompany> employeeCompany = employeeCompanyRepository.findById(companyKey);
        ValidationUtil.isNull(employeeCompany,"EmployeeCompany","companyKey",companyKey);
        return employeeCompanyMapper.toDto(employeeCompany.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeCompanyDTO create(EmployeeCompany resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setCompanyKey(snowflake.nextId());
        return employeeCompanyMapper.toDto(employeeCompanyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EmployeeCompany resources) {
        Optional<EmployeeCompany> optionalEmployeeCompany = employeeCompanyRepository.findById(resources.getCompanyKey());
        ValidationUtil.isNull( optionalEmployeeCompany,"EmployeeCompany","id",resources.getCompanyKey());
        EmployeeCompany employeeCompany = optionalEmployeeCompany.get();
        employeeCompany.copy(resources);
        employeeCompanyRepository.save(employeeCompany);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long companyKey) {
        employeeCompanyRepository.deleteById(companyKey);
    }

   //保存 通过员工id和客商id
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBykey(Long employeeKey, Long coompanyKey) {

        EmployeeCompany employeeCompany = new EmployeeCompany();
        employeeCompany.setCompanyKey(coompanyKey);
        employeeCompany.setEmployeeKey(employeeKey);
        employeeCompanyRepository.save(employeeCompany);
    }



    //删除 通过员工id和客商id
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBykey(Long employeeKey,Long coompanyKey) {

        employeeCompanyRepository.deleteEmployeeCompany(employeeKey,coompanyKey);
    }






}
