package com.hqhop.modules.company.service;

import com.hqhop.config.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.taobao.api.ApiException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/1 0001 15:54
 * @description：客商钉钉业务类
 * @modified By：
 * @version: $
 */

public interface CompanyDingService {

//    //新增客商审批
//    void saveApproval(String userId,Long deptId) throws
//            ApiException;

    //客商审批开始
    void startApproval(String processId, String url,String dicValue) throws
            ApiException;

    //客商新增审批
    CompanyUpdate addApprovel(CompanyUpdate resouces,DingUser dingUser) throws ApiException;


    //新增客商审批同意
    void agreeAddApproval(String processId);

    //新增客商审批驳回
    void refuseAddApproval(String processId);

    //新增客商审批撤销
    void terminateAddApproval(String processId);

    //修改客商审批
    void updateApproval(CompanyUpdate resouces, DingUser dingUser) throws
            ApiException;

    //修改客商审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeUpdateApproval(String processId, String dicValue);

    //修改客商审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseUpdateApproval(String processId, String dicValue);

    //修改客商审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateUpdateApproval(String processId, String dicValue);

    //新旧客商数据对比比
    CompanyUpdate getUpdateDetails(CompanyUpdate resouces);

    //客商 停用/启用 审批
    void isAbleApproval(CompanyUpdate resouces, DingUser dingUser) throws
            ApiException;

    //新增客商审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeIsAbleApproval(String processId);

    //客商 停用/启用 审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseIsAbleApproval(String processId);

    //客商 停用/启用 审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateIsAbleApproval(String processId);
}
