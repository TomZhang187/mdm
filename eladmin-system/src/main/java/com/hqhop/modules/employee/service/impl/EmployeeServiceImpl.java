package com.hqhop.modules.employee.service.impl;

import com.hqhop.modules.employee.domain.Employee;
import me.zhengjie.modules.system.service.dto.DeptSmallDTO;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.ValidationUtil;
import com.hqhop.modules.employee.repository.EmployeeRepository;
import com.hqhop.modules.employee.service.EmployeeService;
import com.hqhop.modules.employee.service.dto.EmployeeDTO;
import com.hqhop.modules.employee.service.dto.EmployeeQueryCriteria;
import com.hqhop.modules.employee.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
* @author chengy
* @date 2019-10-21
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Map<String,Object> queryAll(EmployeeQueryCriteria criteria, Pageable pageable){
        Page<Employee> page = employeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(employeeMapper::toDto));
    }

    @Override
    public List<EmployeeDTO> queryAll(EmployeeQueryCriteria criteria){
        return employeeMapper.toDto(employeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public EmployeeDTO findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        ValidationUtil.isNull(employee,"Employee","id",id);
        return employeeMapper.toDto(employee.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDTO create(Employee resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return employeeMapper.toDto(employeeRepository.save(resources));
        /**不为空版本
         * if(employeeRepository.findByEmployeeCardId(resources.getEmployeeCardId()) != null){
         *             throw new EntityExistException(Employee.class,"employee_card_id",resources.getEmployeeCardId());
         *         }
         *         if(employeeRepository.findByEmployeeCode(resources.getEmployeeCode()) != null){
         *             throw new EntityExistException(Employee.class,"employee_code",resources.getEmployeeCode());
         *         }
         *         if(employeeRepository.findByEmployeePhone(resources.getEmployeePhone()) != null){
         *             throw new EntityExistException(Employee.class,"employee_phone",resources.getEmployeePhone());
         *         }
         *         if(employeeRepository.findByEmployeeState(resources.getEmployeeState()) != null){
         *             throw new EntityExistException(Employee.class,"employee_state",resources.getEmployeeState());
         *         }
         *
         *         return employeeMapper.toDto(employeeRepository.save(resources));
         */
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Employee resources) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalEmployee,"Employee","id",resources.getId());
        Employee employee = optionalEmployee.get();
        employee.copy(resources);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee = employeeOptional.get();
        employee.setEmployeeState(0);
        update(employee);
    }

    @Override
    public void download(List<EmployeeDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (EmployeeDTO employeeDTO : queryAll) {
            List depts = employeeDTO.getDepts().stream().map(DeptSmallDTO::getName).collect(Collectors.toList());
            List roles = employeeDTO.getRoles().stream().map(RoleSmallDTO::getName).collect(Collectors.toList());
            Map map = new LinkedHashMap();
            map.put("姓名",employeeDTO.getEmployeeName());
            map.put("工号", employeeDTO.getEmployeeCode());
            map.put("身份证号",employeeDTO.getEmployeeCardId());
            map.put("电话", employeeDTO.getEmployeePhone());
            map.put("性别",employeeDTO.getEmployeeSex());
            map.put("部门",depts);
            map.put("角色",roles);
            map.put("员工状态",employeeDTO.getEmployeeState());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public EmployeeDTO findByEmployeeCode(String employeeCode) {
        return employeeMapper.toDto(employeeRepository.findByEmployeeCode(employeeCode));
    }
}