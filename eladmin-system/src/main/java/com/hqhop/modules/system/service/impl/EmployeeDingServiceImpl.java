package com.hqhop.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.repository.DeptRepository;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.service.EmployeeDingService;
import com.taobao.api.ApiException;
import com.taobao.api.internal.util.TaobaoHashMap;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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



    //同步钉钉用户信息到主数据平台
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDingUser() throws
            ApiException {

        List<OapiUserListbypageResponse.Userlist> list = getDeptUserDetails(1L);
        if(!list.isEmpty()){
            for (OapiUserListbypageResponse.Userlist userlist : list) {

                Employee employee2 = employeeRepository.findByDingId(userlist.getUserid());
               if(employee2!=null){
                   continue;
               }
                Employee employee = new Employee();
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
                employeeRepository.save(employee);;
            }
        }

    }



    //获取部门用户详情列表
    public List<OapiUserListbypageResponse.Userlist> getDeptUserDetails (Long deptId)throws
            ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();
        request.setDepartmentId(1L);
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


}