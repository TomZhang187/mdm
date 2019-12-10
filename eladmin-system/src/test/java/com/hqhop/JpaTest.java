package com.hqhop;

import com.alipay.api.domain.ItemDiagnoseType;
import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.repository.MaterialRepository;
import com.hqhop.modules.material.service.MaterialDingService;
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
import com.taobao.api.ApiException;
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


    @Autowired
    private MaterialDingService materialDingService;

    @Autowired
    private MaterialRepository materialRepository;

   @Transactional
   @Test
   public  void contextLoads() throws
           ApiException {

       Material material = materialRepository.getOne(95L);
       MaterialOperationRecord materialOperationRecord = new MaterialOperationRecord();
       materialOperationRecord.getDataByMaterial(material);
       materialDingService.addApprovel(materialOperationRecord);

   }




}
