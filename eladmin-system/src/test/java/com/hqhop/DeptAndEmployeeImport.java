package com.hqhop;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetOrgUserCountRequest;
import com.dingtalk.api.response.*;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.easyExcel.model.EmployeeModel;
import com.hqhop.easyExcel.model.IncClient;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.service.DeptDingService;
import com.hqhop.modules.system.service.EmployeeDingService;
import com.taobao.api.ApiException;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptAndEmployeeImport {

    @Autowired
    private DeptDingService deptDingService;

    @Autowired
    private EmployeeDingService employeeDingService;

    @Autowired
    private DeptRepository deptRepository;


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



//    @SneakyThrows
//    @Test
//    public void test4() throws
//            ApiException {
//        //创建输入流
//        InputStream inputStream = null;
//
//
//        inputStream = new FileInputStream("D:\\easyExcel\\通讯录.xlsx");
//        Sheet sheet = new Sheet(1,1,EmployeeModel.class);
//        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
//        List<EmployeeModel> types = new LinkedList<>();
//        for (Object student :  typeList){
//            types.add((EmployeeModel)student);
//        }
//
//        for (EmployeeModel type : types) {
//
//
//
//        }
//
//
//
//
//
//                    Employee employee2 = employeeRepository.findByDingId(userlist.getUserid());
//                    Employee employee = new Employee();
//                    if(employee2!=null){
//                        employee.setId(employee2.getId());
//                    }
//                    employee.getDateByResponse(userlist);
//                    String pageBelongDepts = employeeService.getDeptsStr(userlist.getDepartment());
//                    employee.setPageBelongDepts(pageBelongDepts);
//                    List<Long> list1 = Employee.getDeptListByDing(employee.getDingBelongDepts());
//                    Set<Dept> deptSet = new HashSet<>();
//                    for (Long aLong : list1) {
//                        Dept dept = new Dept();
//                        dept = deptRepository.findByDingId(aLong.toString());
//                        deptSet.add(dept);
//
//                    }
//                    employee.setDepts(deptSet);
////                   employeeRepository.save(employee);
//                }
//            }
//
//
//
//    }

    @Test
    public void test5() throws
            ApiException {


        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_org_user_count");
        OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
        request.setOnlyActive(0L);
        request.setHttpMethod("GET");
        OapiUserGetOrgUserCountResponse response = client.execute(request,  DingTalkUtils.getAccessToken());
        System.out.println("企业人数"+response.getCount());


    }













}










