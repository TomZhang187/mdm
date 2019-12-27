package com.hqhop.modules.company.service;

import com.hqhop.common.dingtalk.dingtalkVo.DingUser;
import com.hqhop.modules.company.domain.Contact;
import com.taobao.api.ApiException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/24 0024 20:49
 * @description：联系人审批业务接口
 * @modified By：
 * @version: $
 */
public interface ContactDingService {
    //联系人审批开始回调
    @Transactional(rollbackFor = Exception.class)
    void startApproval(String processId, String url, String dicValue) throws
            ApiException;

    //联系人分配审批
    Contact addApprovel(Contact resouces, DingUser dingUser) throws
            ApiException;

    //分配联系人审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeAddApproval(String processId);

    //分配联系人审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseAddApproval(String processId);

    //分配联系人审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateAddApproval(String processId);

    //修改联系人审批
    void updateApproval(Contact resouces, DingUser dingUser) throws
            ApiException;

    //修改联系人审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeUpdateApproval(String processId, String dicValue);

    //修改联系人审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseUpdateApproval(String processId, String dicValue);

    //修改联系人审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateUpdateApproval(String processId, String dicValue);

    //联系人取消分配
    void removeApproval(Contact resouces, DingUser dingUser) throws
            ApiException;

    //联系人取消分配 审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeRemoveApproval(String processId);

    //联系人取消分配 审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseRemoveApproval(String processId, String dicValue);

    //联系人取消分配 审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateRemoveApproval(String processId, String dicValue);
}
