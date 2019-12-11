package com.hqhop.modules.system.service;

import cn.hutool.json.JSONObject;
import com.hqhop.config.dingtalk.dingtalkVo.ApprovalCallbackVo;
import com.taobao.api.ApiException;

public interface EmployeeDingCallBackService {
    //入职审批回调
    void entryApprovalBack(ApprovalCallbackVo callback) throws ApiException;

    //离职审批回调
    void dimissionApprovalBack(ApprovalCallbackVo callback);
}
