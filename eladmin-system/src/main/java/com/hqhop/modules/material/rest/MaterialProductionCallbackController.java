package com.hqhop.modules.material.rest;

import cn.hutool.json.JSONObject;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.hqhop.aop.log.Log;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.dingtalkVo.ApprovalCallbackVo;
import com.hqhop.modules.material.service.impl.MaterialProductionDingServiceImpl;
import com.hqhop.modules.system.service.DictDetailService;
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
 * @date ：Created in 2019/12/16 0016 10:06
 * @description：生产档案审批回调
 * @modified By：
 * @version: $
 */


@Api(tags = "MaterialProduction钉钉回调")
@RestController
@RequestMapping("ding")
public class MaterialProductionCallbackController {


    @Autowired
    private DictDetailService dictDetailService;

    @Autowired
    private MaterialProductionDingServiceImpl materialProductionDingService;


    @Log("物料基本档案钉钉审批回调")
    @ApiOperation(value = "物料基本档案钉钉审批回调")
    @PutMapping(value = "/materialProductionCallback")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity addCompanyCallback(@RequestBody(required = false) JSONObject requset) throws
            ApiException {

        ApprovalCallbackVo callback = ApprovalCallbackVo.getVoByJSON(requset);
        String type = callback.getType();
        String result = callback.getResult();
        String processId = callback.getProcessInstanceId();
        OapiProcessinstanceGetResponse response = DingTalkUtils.getProcessInfo(processId);
        //审批模板第一个表单必须是操作类型
        String approvalType = response.getProcessInstance().getFormComponentValues().get(0).getValue();
        String dicValue = dictDetailService.getDicValue("material_operation_type",approvalType);

        if("start".equals(callback.getType())){
            //开始回调处理
            String dingUrl = requset.getStr("url");
            materialProductionDingService.startApproval(processId,dingUrl,dicValue);
            return new ResponseEntity(HttpStatus.OK);
        }
        if("3".equals(dicValue)){
            //物料生产档案新增回调
            toAddCompany(callback,dicValue);

        }else  if("4".equals(dicValue)){
            //物料生产档案变更回调
            toUpdateCompany(callback,dicValue);
            return new ResponseEntity(HttpStatus.OK);

        }else if("5".equals(dicValue) || "6".equals(dicValue)){

            //物料生产档案启用 停用
            toIsAbleCompany(callback,dicValue);

        }else {
            System.err.println("钉钉回调审批类型未知/根本拿不到");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    //物料生产档案新增回调
    private void toAddCompany( ApprovalCallbackVo callback,String dicValue){
        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            try {
                materialProductionDingService.agreeAddApproval(callback.getProcessInstanceId());
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
                    materialProductionDingService.refuseAddApproval(callback.getProcessInstanceId());

        }else if("terminate".equals(callback.getType())){
            //撤销操作
            materialProductionDingService.terminateAddApproval(callback.getProcessInstanceId());

        }
    }

    //物料基本档案修改回调
    private void toUpdateCompany( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            try {
                materialProductionDingService.agreeUpdateApproval(callback.getProcessInstanceId(),dicValue);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            materialProductionDingService.refuseUpdateApproval(callback.getProcessInstanceId(),dicValue);


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            materialProductionDingService.terminateUpdateApproval(callback.getProcessInstanceId(),dicValue);
        }
    }

    //物料生产档案停用启用回调
    private void toIsAbleCompany( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
           materialProductionDingService.agreeIsAbleApproval(callback.getProcessInstanceId());

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            materialProductionDingService.refuseIsAbleApproval(callback.getProcessInstanceId());


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            materialProductionDingService.terminateIsAbleApproval(callback.getProcessInstanceId());
        }
    }











}
