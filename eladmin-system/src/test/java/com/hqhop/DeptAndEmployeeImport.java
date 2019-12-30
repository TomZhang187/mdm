package com.hqhop;


import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetOrgUserCountRequest;
import com.dingtalk.api.response.*;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.domain.U8cDomain.U8cAccount;
import com.hqhop.modules.company.domain.U8cDomain.U8cContact;
import com.hqhop.modules.company.repository.AccountRepository;
import com.hqhop.modules.company.repository.CompanyInfoRepository;
import com.hqhop.modules.company.repository.ContactRepository;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.DeptDingService;
import com.hqhop.modules.system.service.EmployeeDingService;
import com.hqhop.modules.system.service.EmployeeService;
import com.hqhop.utils.HttpUtil;
import com.taobao.api.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptAndEmployeeImport {

    @Autowired
    private DeptDingService deptDingService;

    @Autowired
    private EmployeeDingService employeeDingService;

    @Autowired
    private DeptRepository deptRepository;


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AccountRepository accountRepository;


    //部门导入
    @Test
    public void test1() {

        try {
            deptDingService.syncDeptToDingDept();
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }

    //员工导入导入
    @Test
    public void test2() {

        try {
            employeeDingService.syncDingUser();
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }



    @Test
    public void test3() throws
            ApiException {

        int i = 0;
        List<OapiDepartmentListResponse.Department> deptsLists = deptDingService.getDeptsLists(null);
        for (OapiDepartmentListResponse.Department deptsList : deptsLists) {

            List<OapiUserListbypageResponse.Userlist> list = employeeDingService.getDeptUserDetails(deptsList.getId());
                for (OapiUserListbypageResponse.Userlist userlist : list) {
                    i++;
                }
        }
        System.out.println("统计" + i);
    }


     //通过excel员工导入
     @Test
    public void test4() throws
            ApiException {


//        OapiUserGetResponse userInfo = DingTalkUtils.getUserInfo("24332817681293637");
//         System.out.println(userInfo.getJobnumber());
//



         try {
            employeeDingService.syncDingUser("D:\\easyExcel\\通讯录.xlsx");
        } catch (ApiException e) {
            e.printStackTrace();
        }


         List<Employee> all = employeeRepository.findAll();
         for (Employee employee : all) {

             OapiUserGetResponse userInfo = DingTalkUtils.getUserInfo(employee.getDingId());
             if(userInfo.getDepartment() !=null && userInfo.getDepartment().size()!=0){
                 employee.setDingBelongDepts(userInfo.getDepartment().toString());
             }
             employee.setEmployeeCode(userInfo.getJobnumber());
             employeeRepository.save(employee);


         }
    }

    @Test
    public void test6() throws
            ApiException {


        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_org_user_count");
        OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
        request.setOnlyActive(0L);
        request.setHttpMethod("GET");
        OapiUserGetOrgUserCountResponse response = client.execute(request,  DingTalkUtils.getAccessToken());
        System.out.println("企业人数"+response.getCount());


    }






}










