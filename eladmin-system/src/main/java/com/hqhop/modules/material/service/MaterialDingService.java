package com.hqhop.modules.material.service;

import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.taobao.api.ApiException;

public interface MaterialDingService {
    //物料新增审批
    void addApprovel(MaterialOperationRecord resources) throws
            ApiException;
}
