package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.taobao.api.ApiException;
import org.springframework.transaction.annotation.Transactional;

public interface MaterialDingService {
    //物料新增审批
    void addApprovel(MaterialOperationRecord resources) throws
            ApiException;

    //物料新增审批通过
    @Transactional(rollbackFor = Exception.class)
    void agreeAddApproval(String processId);
}
