package com.hqhop.modules.system.service;

import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.hqhop.modules.system.domain.Employee;
import com.taobao.api.ApiException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/28 0028 10:19
 * @description：员工钉钉业务接口
 * @modified By：
 * @version: $
 */
public interface EmployeeDingService {


    //同步钉钉用户信息到主数据平台
    @Transactional(rollbackFor = Exception.class)
    void syncDingUser()throws
            ApiException;


    //
    //   //获取用户详情 同步钉钉用户数据
         @Transactional(rollbackFor = Exception.class)
         void syncDingUser(String fileName) throws
                 ApiException;

    //获取部门用户详情列表
    List<OapiUserListbypageResponse.Userlist> getDeptUserDetails(Long deptId)throws
            ApiException;

    //获取用户详情
    Employee getDingUserDetails(String dingUserId)throws
            ApiException;
}
