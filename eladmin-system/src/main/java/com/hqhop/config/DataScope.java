package com.hqhop.config;

import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.domain.Role;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.repository.RoleRepository;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.RoleService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.RoleSmallDTO;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.hqhop.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据权限配置
 * @author Zheng Jie
 * @date 2019-4-1
 */
@Component
public class  DataScope {

    private final String[] scopeType = {"全部","本级","自定义"};

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DeptService deptService;

    public Set<Long> getDeptIds() {

        UserDTO user = userService.findByName(SecurityUtils.getUsername());
        Employee employee = employeeRepository.findByDingId(user.getEmployee().getDingId());
        user.setDepts(employee.getDeptsSet());

        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();

        // 查询用户角色
        Set<Role> roleSet =roleRepository.findByUsers_Id(user.getId());

        for (Role role : roleSet) {

            if (scopeType[0].equals(role.getDataScope())) {

             return new HashSet<>() ;
//                return  null;
            }

            // 存储本级的数据权限
            if (scopeType[1].equals(role.getDataScope())) {
                Set<Long> deptsSet = user.getDepts();
                for (Long aLong : deptsSet) {
                    deptIds.add(aLong);
                }
            }

            // 存储自定义的数据权限
            if (scopeType[2].equals(role.getDataScope())) {
                Set<Dept> depts = deptService.findByRoleIds(role.getId());
                for (Dept dept : depts) {
                    deptIds.add(dept.getId());
                    List<Dept> deptChildren = deptService.findByPid(dept.getId());
                    if (deptChildren != null && deptChildren.size() != 0) {
                        deptIds.addAll(getDeptChildren(deptChildren));
                    }
                }
            }
        }
        return deptIds;
    }


    public List<Long> getDeptChildren(List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept!=null && dept.getEnabled()){
                        List<Dept> depts = deptService.findByPid(dept.getId());
                        if(deptList!=null && deptList.size()!=0){
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }
}
