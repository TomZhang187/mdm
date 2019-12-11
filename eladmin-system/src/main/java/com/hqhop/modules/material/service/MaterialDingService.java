package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.taobao.api.ApiException;
import org.springframework.transaction.annotation.Transactional;

public interface MaterialDingService {
    //物料基本档案审批开始回调
    @Transactional(rollbackFor = Exception.class)
    void startApproval(String processId, String url, String dicValue) throws
            ApiException;

    //物料新增审批
    void addApprovel(Material  resources) throws
            ApiException;

    //物料新增审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeAddApproval(String processId);

    //物料新增审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseAddApproval(String processId);

    //物料新增审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateAddApproval(String processId);

    //物料基本档案变更审批
    void updateApprovel(Material resources) throws
            ApiException;

    //物料变更审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeUpdateApproval(String processId, String dicValue);

    //物料变更审批驳回
    @Transactional(rollbackFor = Exception.class)
    void refuseUpdateApproval(String processId, String dicValue);

    //物料变更审批撤销
    @Transactional(rollbackFor = Exception.class)
    void terminateUpdateApproval(String processId, String dicValue);


}
