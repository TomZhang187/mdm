package com.hqhop.modules.company.rest;


import cn.hutool.json.JSONObject;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.hqhop.aop.log.Log;
import com.hqhop.config.dingtalk.DingTalkUtils;
import com.hqhop.modules.company.service.CompanyDingService;
import com.hqhop.modules.system.service.DictDetailService;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.SocketTimeoutException;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/2 0002 18:53
 * @description：客商钉钉回调接口
 * @modified By：
 * @version: $
 */


@Api(tags = "CompanyInfo钉钉回调")
@RestController
@RequestMapping("ding")
public class CompanyCallbackController {

    @Autowired
    private CompanyDingService companyDingService;

    @Autowired
    private DictDetailService dictDetailService;

    @Log("客商钉钉审批回调")
    @ApiOperation(value = "客商钉钉审批回调")
    @PutMapping(value = "/companyCallback")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity  addCompanyCallback( @RequestBody(required = false) JSONObject requset) throws
            ApiException {{

            String type = requset.getStr("type");
            String result = requset.getStr("result");
            String processId = requset.getStr("processInstanceId");
        OapiProcessinstanceGetResponse response = DingTalkUtils.getProcessInfo(processId);
        //审批模板第一个表单必须是操作类型
        String approvalType = response.getProcessInstance().getFormComponentValues().get(0).getValue();
        String dicValue = dictDetailService.getDicValue("company_operation_type",approvalType);

        if("start".equals(type)){
            //开始回调处理
            String dingUrl = requset.getStr("url");
            companyDingService.startApproval(processId,dingUrl,dicValue);

            return new ResponseEntity(HttpStatus.OK);
        }


        //1 新增 2 修改 3停用 4启用....更多对照字典
        if("1".equals(dicValue)){
            if("finish".equals(type) && "agree".equals(result)){
                //审批同意操作
                companyDingService.agreeAddApproval(processId);

            }else if("finish".equals(type) && "refuse".equals(result)){
                //拒绝操作
                companyDingService.refuseAddApproval(processId);

            }else if("terminate".equals(type)){
                //撤销操作
                companyDingService.terminateAddApproval(processId);
            }

            //1 新增 2 修改 3停用 4启用....更多对照字典
        }else  if("2".equals(dicValue)){

            if("finish".equals(type) && "agree".equals(result)){
                //审批同意操作
                 companyDingService.agreeUpdateApproval(processId,dicValue);

            }else if("finish".equals(type) && "refuse".equals(result)){
                //拒绝操作
                companyDingService.refuseUpdateApproval(processId,dicValue);

            }else if("terminate".equals(type)){
                //撤销操作
                companyDingService.terminateUpdateApproval(processId,dicValue);
            }
            //1 新增 2 修改 3停用 4启用....更多对照字典
        }else  if("3".equals(dicValue) || "4".equals(dicValue)){

            if("finish".equals(type) && "agree".equals(result)){
                //审批同意操作
                companyDingService.agreeIsAbleApproval(processId);

            }else if("finish".equals(type) && "refuse".equals(result)){
                //拒绝操作
                companyDingService.refuseIsAbleApproval(processId);

            }else if("terminate".equals(type)){
                //撤销操作
                companyDingService.terminateIsAbleApproval(processId);
            }
        }else {
            System.err.println("钉钉回调审批类型未知/根本拿不到");
        }
        }

        return new ResponseEntity(HttpStatus.OK);
    }


}
