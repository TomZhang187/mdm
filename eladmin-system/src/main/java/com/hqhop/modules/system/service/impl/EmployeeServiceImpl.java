package com.hqhop.modules.system.service.impl;

import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.service.dto.EmployeeSmallDTO;
import com.hqhop.utils.ValidationUtil;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.EmployeeService;
import com.hqhop.modules.system.service.dto.EmployeeDTO;
import com.hqhop.modules.system.service.dto.EmployeeQueryCriteria;
import com.hqhop.modules.system.service.mapper.EmployeeMapper;
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
import java.util.stream.Collectors;

/**
* @author zf
* @date 2019-11-28
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Map<String,Object> queryAll(EmployeeQueryCriteria criteria, Pageable pageable){
        Page<Employee> page = employeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    @Override
    public List<Employee> queryAll(EmployeeQueryCriteria criteria){

        return employeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    public EmployeeDTO findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        ValidationUtil.isNull(employee,"Employee","id",id);
        return employeeMapper.toDto(employee.get());
    }

    //通过工号查询
    @Override
    public Employee getEmployeeByCode(String code) {
       Employee employee = employeeRepository.findByEmployeeCode(code);
       if(employee!=null){
           employee.setDepts(null);
           return employee;
       }
     return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDTO create(Employee resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        return employeeMapper.toDto(employeeRepository.save(resources));
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
        employeeRepository.deleteById(id);
    }

    // 拿到字符串部门列表
    @Override
    public String getDeptsStr(String depts) {



//        String str =deptRepository.findByDingId("1").getName()+"/";

        Dept dept2 = deptRepository.findByDingId("1");
        long rootId = dept2.getId();

        String str ="";
       List<Long> list = Employee.getDeptListByDing(depts);
       if(list != null && !list.isEmpty()){

           for (int i=1;i<list.size();i++){
               String strs = "";
               Dept dept = deptRepository.findByDingId(list.get(i).toString());
               strs = dept.getName();
              while(dept.getPid()!=rootId && dept.getPid()!=null){

                  String ss = deptRepository.findNameById(dept.getPid());
                  strs=ss+"-"+strs;
                  dept =deptRepository.findByKey(dept.getPid());
              }
             str+= strs+" /";
           }

           if(str!=null&&str.length()>0){
               str = str.toString().substring(0,(str.toString()).length()-1);
           }

       }
        return str;
    }
}
