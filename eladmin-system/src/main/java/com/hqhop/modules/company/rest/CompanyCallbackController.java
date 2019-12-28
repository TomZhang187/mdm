package com.hqhop.modules.company.rest;


import cn.hutool.json.JSONObject;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.hqhop.aop.log.Log;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.dingtalkVo.ApprovalCallbackVo;
import com.hqhop.modules.company.service.CompanyDingService;
import com.hqhop.modules.company.service.ContactDingService;
import com.hqhop.modules.company.service.AccountDingService;
import com.hqhop.modules.system.service.DictDetailService;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

   @Autowired
   private  AccountDingService accountDingService;


    @Autowired
    private ContactDingService contactDingService;



    @Log("客商钉钉审批回调")
    @ApiOperation(value = "客商钉钉审批回调")
    @PutMapping(value = "/companyCallback")
//    @PreAuthorize("hasAnyRole('ADMIN','COMPANYINFO_ALL','COMPANYINFO_SELECT')")
    public ResponseEntity  addCompanyCallback( @RequestBody(required = false) JSONObject requset) throws
            ApiException {{

        ApprovalCallbackVo callback = ApprovalCallbackVo.getVoByJSON(requset);
            String type = callback.getType();
            String result = callback.getResult();
            String processId = callback.getProcessInstanceId();
        OapiProcessinstanceGetResponse response = DingTalkUtils.getProcessInfo(processId);
        //审批模板第一个表单必须是操作类型
        String approvalType = response.getProcessInstance().getFormComponentValues().get(0).getValue();
        String dicValue = dictDetailService.getDicValue("company_operation_type",approvalType);

        if("start".equals(callback.getType())){
            //开始回调处理
            String dingUrl = requset.getStr("url");
            companyDingService.startApproval(processId,dingUrl,dicValue);
            return new ResponseEntity(HttpStatus.OK);
        }

        if("1".equals(dicValue)){ //1 新增....更多对照字典

            toAddCompany(callback,dicValue);
        }else  if("2".equals(dicValue)){   // 2 修改....更多对照字典

            toUpdateCompany(callback,dicValue);
        }else  if("3".equals(dicValue) || "4".equals(dicValue)){ //3停用 4启用....更多对照字典
            toIsAbleCompany(callback,dicValue);

        }else  if("5".equals(dicValue)){  //联系人新增....更多对照字典
            toAddContact(callback,dicValue);

        }else  if("6".equals(dicValue)){   //联系人修改....更多对照字典
            toUpdateContact(callback,dicValue);
        }else  if("10".equals(dicValue)){  //联系人解绑....更多对照字典
            toRemoveContact(callback,dicValue);
        }else  if("7".equals(dicValue)) {  //客商账户新增....更多对照字典
            toAddAccount(callback,dicValue);
        }else  if("8".equals(dicValue)) {  //客商账户修改....更多对照字典
            toUpdateAccount(callback,dicValue);
        }else  if("11".equals(dicValue)) {  //客商账户解绑....更多对照字典
            toRemoveAccount(callback,dicValue);
        }
        else {
            System.err.println("钉钉回调审批类型未知/根本拿不到");
        }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

   //客商新增回调
    private void toAddCompany( ApprovalCallbackVo callback,String dicValue){
        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            companyDingService.agreeAddApproval(callback.getProcessInstanceId());

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            companyDingService.refuseAddApproval(callback.getProcessInstanceId());

        }else if("terminate".equals(callback.getType())){
            //撤销操作
            companyDingService.terminateAddApproval(callback.getProcessInstanceId());
        }
    }

    //客商修改回调
    private void toUpdateCompany( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            companyDingService.agreeUpdateApproval(callback.getProcessInstanceId(),dicValue);

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            companyDingService.refuseUpdateApproval(callback.getProcessInstanceId(),dicValue);


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            companyDingService.terminateUpdateApproval(callback.getProcessInstanceId(),dicValue);
        }
    }

    //客商停用启用回调
    private void toIsAbleCompany( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            companyDingService.agreeIsAbleApproval(callback.getProcessInstanceId());

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            companyDingService.refuseIsAbleApproval(callback.getProcessInstanceId());


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            companyDingService.terminateIsAbleApproval(callback.getProcessInstanceId());
        }
    }


    //联系人新增回调
    private void toAddContact( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            contactDingService.agreeAddApproval(callback.getProcessInstanceId());

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            contactDingService.refuseAddApproval(callback.getProcessInstanceId());


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            contactDingService.terminateAddApproval(callback.getProcessInstanceId());
        }
    }

    //联系人修改回调
    private void toUpdateContact( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            contactDingService.agreeUpdateApproval(callback.getProcessInstanceId(),dicValue);

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            contactDingService.refuseUpdateApproval(callback.getProcessInstanceId(),dicValue);


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            contactDingService.terminateUpdateApproval(callback.getProcessInstanceId(),dicValue);
        }
    }

    //联系人解绑回调
    private void toRemoveContact( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            contactDingService.agreeRemoveApproval(callback.getProcessInstanceId());

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            contactDingService.refuseRemoveApproval(callback.getProcessInstanceId(),dicValue);


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            contactDingService.terminateRemoveApproval(callback.getProcessInstanceId(),dicValue);
        }
    }


    //客商账户新增回调
    private void toAddAccount( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            accountDingService.agreeAddApproval(callback.getProcessInstanceId());

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            accountDingService.refuseAddApproval(callback.getProcessInstanceId());


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            accountDingService.terminateAddApproval(callback.getProcessInstanceId());
        }
    }

    //客商账户修改回调
    private void toUpdateAccount( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
            accountDingService.agreeUpdateApproval(callback.getProcessInstanceId(),dicValue);

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            accountDingService.refuseUpdateApproval(callback.getProcessInstanceId(),dicValue);


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            accountDingService.terminateUpdateApproval(callback.getProcessInstanceId(),dicValue);
        }
    }

    //客商账户解绑回调
    private void toRemoveAccount( ApprovalCallbackVo callback,String dicValue){

        if("finish".equals(callback.getType()) && "agree".equals(callback.getResult())){
            //审批同意操作
           accountDingService.agreeRemoveApproval(callback.getProcessInstanceId());

        }else if("finish".equals(callback.getType()) && "refuse".equals(callback.getResult())){
            //拒绝操作
            accountDingService.refuseRemoveApproval(callback.getProcessInstanceId(),dicValue);


        }else if("terminate".equals(callback.getType())){
            //撤销操作
            accountDingService.terminateRemoveApproval(callback.getProcessInstanceId(),dicValue);
        }
    }
}
