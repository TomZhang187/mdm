package com.hqhop;

import com.alipay.api.domain.ItemDiagnoseType;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.domain.Role;
import com.hqhop.modules.system.domain.User;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.repository.RoleRepository;
import com.hqhop.modules.system.repository.UserRepository;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DeptService deptService;

   @Transactional
   @Test
   public  void contextLoads() {


    Employee employee = employeeRepository.findByDingId("223704406424012457");


       Set<Dept> list = new HashSet<>();
       list = deptService.getBelongSubsidiary(employee.getDeptsSet());
//       for (Long dept : employee.getDeptsSet()) {
//           Dept dept1 = deptRepository.findByKey(dept);
//           Dept dept2 = null;
//           while (dept1.getPid() !=0){
//               dept2 = dept1;
//               dept1= deptRepository.findByKey(dept1.getPid());
//           }
//           if(dept2!= null){
//               list.add(dept2);
//           }
//
//       }
       for (Dept dept : list) {
           System.out.println(dept.getName());
       }



//
//       Set<Role> set = roleRepository.findByUsers_Id(1L);
//       System.out.println(set.toString());
//
////        System.out.println(user);
    }
}
