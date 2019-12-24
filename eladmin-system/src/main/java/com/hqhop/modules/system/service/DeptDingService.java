package com.hqhop.modules.system.service;

import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.hqhop.modules.system.domain.dingVo.deptVo.DingDeptDetailVo;
import com.hqhop.modules.system.domain.Dept;
import com.taobao.api.ApiException;

import java.util.List;

public interface DeptDingService {
    //创建钉钉部门
    String createDignDept(Dept dept) throws
            ApiException;

    //更新钉钉部门
    void updateDingDept(Dept dept)throws
             ApiException;

    //删除钉钉部门
    void deleteDingDept(Dept dept)throws
             ApiException;

    //获取部门列表
    List<OapiDepartmentListResponse.Department> getDeptsLists(String deptId)throws
            ApiException;

    //同步钉钉部门到后台部门
    void syncDeptToDingDept()throws
            ApiException;

    //获取部门详情
    DingDeptDetailVo getDeptDetails(Dept resource)throws
            ApiException;
}
