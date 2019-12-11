package com.hqhop.modules.system.service.rest.DingCallbackRest;

import cn.hutool.json.JSONObject;
import com.hqhop.aop.log.Log;
import com.hqhop.config.dingtalk.dingtalkVo.ApprovalCallbackVo;
import com.hqhop.modules.system.service.EmployeeDingCallBackService;
import com.hqhop.modules.system.service.impl.DingCallBackImpl.EmployeeDingCallBackImpl;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/29 0029 15:54
 * @description：员工钉钉回调接口
 * @modified By：
 * @version: $
 */
@Api(tags = "Employee钉钉回调")
@RestController
@RequestMapping("ding")
public class EmployeeDingCallBackController {

    @Autowired
    private EmployeeDingCallBackService employeeDingCallBackService;

    @Log("入职审批回调")
    @ApiOperation(value = "入职钉钉审批回调")
    @PutMapping(value = "/entryCallback")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity entryApproval(@RequestBody(required = false) JSONObject requset) throws
            ApiException {

        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("离职审批回调")
    @ApiOperation(value = "离职钉钉审批回调")
    @PutMapping(value = "/dimissionCallback")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity dimissionApprovalBack(@RequestBody(required = false) JSONObject requset) throws
            ApiException {


        ApprovalCallbackVo callback = ApprovalCallbackVo.getVoByJSON(requset);
        if("agree".equals(callback.getResult())){
            employeeDingCallBackService.dimissionApprovalBack(callback);
        }

        return new ResponseEntity(HttpStatus.OK);
    }














}
