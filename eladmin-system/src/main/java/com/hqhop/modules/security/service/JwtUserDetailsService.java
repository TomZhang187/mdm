package com.hqhop.modules.security.service;

import com.hqhop.exception.BadRequestException;
import com.hqhop.modules.security.security.JwtUser;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.JobSmallDTO;
import com.hqhop.modules.system.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class    JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtPermissionService permissionService;

//    //通过用户名加载用户信息方法
//    @Override
//    public UserDetails loadUserByUsername(String username){
//
//        UserDTO user = userService.findByName(username);
//        if(user.getEmployee() != null){
//            Employee employee = employeeRepository.findByDingId(user.getEmployee().getDingId());
//            user.setDepts(employee.getDeptsSet());
//        }
//
//        if (user == null) {
//            throw new BadRequestException("账号不存在");
//        } else {
//            return createJwtUser(user);
//        }
//    }

    //通过用户名（钉钉ID）加载用户信息方法
    @Override
    public UserDetails loadUserByUsername(String username){

        UserDTO user = userService.findByDingId(username);
        if(user.getEmployee() != null){
            Employee employee = employeeRepository.findByDingId(user.getEmployee().getDingId());
            user.setDepts(employee.getDeptsSet());
        }

        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            return createJwtUser(user);
        }
    }


    public UserDetails createJwtUser(UserDTO user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getEmployee()!=null?user.getEmployee().getEmployeeName():null,
                user.getEmployee()!=null?user.getEmployee().getDingId():null,
                user.getEmployee()!=null?user.getEmployee().getId():null,
                user.getEmployee()!=null?user.getEmployee().getEmployeeCode():null,
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                user.getPhone(),
                Optional.ofNullable(user.getEmployee()!=null?user.getEmployee().getEmployeeName():null).orElse(null),
                user.getDepts(),
                Optional.ofNullable(user.getJob()).map(JobSmallDTO::getName).orElse(null),
                permissionService.mapToGrantedAuthorities(user),
                user.getEnabled(),
                user.getCreateTime(),
                user.getDduserid(),
                user.getEmpnum(),
                user.getLastPasswordResetTime()
        );
    }
}
