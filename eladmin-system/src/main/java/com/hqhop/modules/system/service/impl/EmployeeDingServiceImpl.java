package com.hqhop.modules.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.easyexcels.excelread.EmployeeExcelUtils;
import com.hqhop.easyexcels.model.EmployeeModel;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.DeptDingService;
import com.hqhop.modules.system.service.EmployeeDingService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/28 0028 10:21
 * @description：员工钉钉业务实现类
 * @modified By：
 * @version: $
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmployeeDingServiceImpl implements EmployeeDingService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private DeptDingService deptDingService;



    //部门用户详情同步钉钉用户信息到主数据平台
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDingUser() throws
            ApiException {



        List<OapiDepartmentListResponse.Department> deptsLists = deptDingService.getDeptsLists(null);
        for (OapiDepartmentListResponse.Department deptsList : deptsLists) {

            List<OapiUserListbypageResponse.Userlist> list = getDeptUserDetails(deptsList.getId());
            if(list.size()==0){
                continue;
            }
            if(!list.isEmpty()){
                for (OapiUserListbypageResponse.Userlist userlist : list) {

                    Employee employee2 = employeeRepository.findByDingId(userlist.getUserid());
                    Employee employee = new Employee();
                    if(employee2!=null){
                        employee.setId(employee2.getId());
                    }
                    employee.getDateByResponse(userlist);
                    String pageBelongDepts = employeeService.getDeptsStr(userlist.getDepartment());
                    employee.setPageBelongDepts(pageBelongDepts);
                    List<Long> list1 = Employee.getDeptListByDing(employee.getDingBelongDepts());
                    Set<Dept> deptSet = new HashSet<>();
                    for (Long aLong : list1) {
                        Dept dept = new Dept();
                        dept = deptRepository.findByDingId(aLong.toString());
                        deptSet.add(dept);

                    }
                    employee.setDepts(deptSet);
                    employeeRepository.save(employee);
                }
            }

        }
    }

//   //获取用户详情 同步钉钉用户数据
     @Override
     @Transactional(rollbackFor = Exception.class)
     public  void syncDingUser(String fileName) throws
             ApiException {
         //创建输入流
         InputStream inputStream = null;
         List<EmployeeModel> types = EmployeeExcelUtils.readEmployeeExcel(fileName);

         for (EmployeeModel type : types) {


             Employee employee2 = employeeRepository.findByDingId(type.getEmployeeId());
             Employee employee = new Employee();
             if(employee2!=null){
                employee.setId(employee2.getId());
             }
             OapiUserGetResponse userInfo = DingTalkUtils.getUserInfo(type.getEmployeeId());
             if(userInfo.getDingId() == null){
                   employee.getDataByEmployeeModel(type);
                 employeeRepository.save(employee);
                 continue;
             }
             employee.getDateByResponse(userInfo);
             if(userInfo.getDepartment()!=null){
                 String pageBelongDepts = employeeService.getDeptsStr(userInfo.getDepartment().toString());
                 employee.setPageBelongDepts(pageBelongDepts);
                 List<Long> list1 = userInfo.getDepartment();
                 Set<Dept> deptSet = new HashSet<>();
                 for (Long aLong : list1) {
                     Dept dept = new Dept();
                     dept = deptRepository.findByDingId(aLong.toString());
                     deptSet.add(dept);
                 }
                 employee.setDepts(deptSet);
             }

             employeeRepository.save(employee);
         }
     }


    //获取部门用户详情列表
    @Override
    public List<OapiUserListbypageResponse.Userlist> getDeptUserDetails(Long deptId)throws
            ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();
        if(deptId != null){
            request.setDepartmentId(deptId);
        }else {
            request.setDepartmentId(1L);
        }
        request.setOffset(0L);
        request.setSize(10L);
        request.setOrder("entry_desc");
        request.setHttpMethod("GET");
        OapiUserListbypageResponse execute = client.execute(request, DingTalkUtils.getAccessToken());
        List<OapiUserListbypageResponse.Userlist> list = execute.getUserlist();
              return  list;
    }


    //获取用户详情
    @Override
    public Employee getDingUserDetails(String dingUserId)
            throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(dingUserId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, DingTalkUtils.getAccessToken());
        Employee employee = new Employee();
        employee.getDateByResponse(response);
        List<Long> list = response.getDepartment();
        JSONObject jsonObject = JSONObject.parseObject(response.getIsLeaderInDepts());
        Boolean isLeader = false;
        for (Long aLong : list) {
          isLeader = Boolean.valueOf(jsonObject.getString(aLong.toString()));
             if(isLeader) {
                 break;
             }
        }
           employee.setLeader(isLeader);


        return  employee;
}

    public List<String> getServiceWorkers() throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob");
        OapiSmartworkHrmEmployeeQueryonjobRequest req = new OapiSmartworkHrmEmployeeQueryonjobRequest();
        req.setStatusList("2,3");
        req.setOffset(15L);
        req.setSize(5L);
        OapiSmartworkHrmEmployeeQueryonjobResponse response = client.execute(req , DingTalkUtils.getAccessToken());
        if(response.getErrcode()==0){

            return response.getResult().getDataList();
        }


        return null;
    }





















}
