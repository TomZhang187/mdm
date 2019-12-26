package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.taobao.api.ApiException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/13 0013 17:20
 * @description：物料生产档案钉钉审批业务接口
 * @modified By：
 * @version: $
 */
public interface MaterialProducionDingService {



    //物料生产档案审批开始回调
    @Transactional(rollbackFor = Exception.class)
    void startApproval(String processId, String url, String dicValue) throws
            ApiException;

    //生产档案新增审批
    void addApprovel(MaterialOperationRecord resouces) throws
            ApiException;

    //物料生产档案新增审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeAddApproval(String processId) throws
            ApiException
        ;

    //物料生产档案新增审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseAddApproval(String processId);

    //物料生产档案新增审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateAddApproval(String processId);

    //物料生产档案变更审批
    void updateApprovel(MaterialOperationRecord resources) throws
            ApiException;

    //物料生产档案变更审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeUpdateApproval(String processId, String dicValue) throws
            ApiException ;

    //物料生产档案变更审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseUpdateApproval(String processId, String dicValue);

    //物料生产档案变更审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateUpdateApproval(String processId, String dicValue);

    //物料生产档案 停用/启用 审批
    void isAbleApproval(Integer id) throws
            ApiException;

    //物料生产档案 停用/启用 审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeIsAbleApproval(String processId);

    //物料生产档案 停用/启用 审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseIsAbleApproval(String processId);

    //物料生产档案 停用/启用 审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateIsAbleApproval(String processId);
}
