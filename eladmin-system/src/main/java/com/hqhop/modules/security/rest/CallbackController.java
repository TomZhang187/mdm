//package com.hqhop.modules.security.rest;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.dingtalk.api.DefaultDingTalkClient;
//import com.dingtalk.api.DingTalkClient;
//import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
//import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
//import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
//import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
//import com.dingtalk.oapi.lib.aes.Utils;
//import com.hqhop.common.dingtalk.DingTalkConstant;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//import java.util.Map;
//
///**
// * E应用回调信息处理
// */
//@RestController
//public class CallbackController {
//
//    private static final Logger bizLogger = LoggerFactory.getLogger("BIZ_CALLBACKCONTROLLER");
//    private static final Logger mainLogger = LoggerFactory.getLogger(CallbackController.class);
//
//    private static final String TOKEN = "HQHOP_ABCDE";
//    private static final String ENCODING_AES_KEY = "234eeglba9194j443343k4jh3j453453452dsfsdfsd";
//
//    /**
//     * 创建套件后，验证回调URL创建有效事件（第一次保存回调URL之前）
//     */
//    private static final String CHECK_URL = "check_url";
//
//    /**
//     * 审批任务回调
//     */
//    private static final String BPMS_TASK_CHANGE = "bpms_task_change";
//
//    /**
//     * 审批实例回调
//     */
//    private static final String BPMS_INSTANCE_CHANGE = "bpms_instance_change";
//
//    /**
//     * 相应钉钉回调时的值
//     */
//    private static final String CALLBACK_RESPONSE_SUCCESS = "success";
//
//
////    private String[] as = {DingTalkConstant.PROCESSCODE_CONTRACT_ADD, DingTalkConstant.PROCESSCODE_CONTRACT_CHANGE,};
//
////    @Resource
////    private ContractService contractService;
////
////    @Resource
////    private PaymentService paymentService;
//
//
////    @RequestMapping(value = "/callback", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> callback(@RequestParam(value = "signature", required = false) String signature,
//                                        @RequestParam(value = "timestamp", required = false) String timestamp,
//                                        @RequestParam(value = "nonce", required = false) String nonce,
//                                        @RequestBody(required = false) JSONObject json) {
//        String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
//        try {
//            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(TOKEN, ENCODING_AES_KEY,
//                    DingTalkConstant.CORPID);
//
//            //从post请求的body中获取回调信息的加密数据进行解密处理
//            String encryptMsg = json.getString("encrypt");
//            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
//            JSONObject obj = JSON.parseObject(plainText);
//
//            //根据回调数据类型做不同的业务处理
//            String eventType = obj.getString("EventType");
//            if (BPMS_TASK_CHANGE.equals(eventType)) {
//                bizLogger.info("收到审批任务进度更新: " + plainText);
//
//                // 收到审批任务进度更新:
//                // {"processInstanceId":"c529e8c6-66a8-4590-b79c-42058954ba9a","corpId":"ding1d7e8b088fab194f35c2f4657eb6378f","EventType":"bpms_task_change","businessId":"201908091058000275439","title":"陈光跃提交的分包工程合同","type":"start","createTime":1565319667000,"processCode":"PROC-1DF9A928-FF53-4EB0-8D31-1E66AD3C1D28","bizCategoryId":"","staffId":"4258231637652962","taskId":61920249919}
//                //  收到审批任务进度更新: {"processInstanceId":"89e61c17-22c7-45bd-89ab-d17a1c51e70b","finishTime":1565319992000,"corpId":"ding1d7e8b088fab194f35c2f4657eb6378f","EventType":"bpms_task_change","businessId":"201908090956000472764","remark":"","title":"田维维提交的非生产物资/固定资产采购申请","type":"finish","result":"agree","createTime":1565315808000,"processCode":"PROC-D0DA718D-5800-49DB-B6C4-CC258003DDFD","bizCategoryId":"","staffId":"1806350712968745","taskId":61919533393}
//                //todo: 实现审批的业务逻辑，如发消息
//            } else if (BPMS_INSTANCE_CHANGE.equals(eventType)) {
//                bizLogger.info("收到审批实例状态更新: " + plainText);
//                //todo: 实现审批的业务逻辑，如发消息
//
////                String processCode = obj.getString("processCode");
//                // 获取需要监控流程的数据
////                String [] ass = DingTalkConstant.PROCESSCODES;
////                if(Arrays.binarySearch(ass, processCode) < 0){ // 只监控在数组中的数据
//                //调用分发接口
//                dispenseCallback(obj);
////                }
//                //监控所有的流程
///*                String processInstanceId = obj.getString("processInstanceId");
//                if (obj.containsKey("result") && obj.getString("result").equals("agree")) {
//                    MessageUtil.sendMessageToOriginator(processInstanceId);
//                }*/
//            } else {
//                // 其他类型事件处理
//            }
//
//            // 返回success的加密信息表示回调处理成功
//            return dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(), Utils.getRandomStr(8));
//        } catch (Exception e) {
//            //失败的情况，应用的开发者应该通过告警感知，并干预修复
//            mainLogger.error("process callback failed！" + params, e);
//            return null;
//        }
//
//    }
//
//    /**
//     * 分发处理回调审批流程，根据流程不同，调用不同模块的业务流程
//     * {
//     * "processInstanceId": "c529e8c6-66a8-4590-b79c-42058954ba9a", // 审批实例id
//     * "finishTime": 1565321123000, // 审批结束时间
//     * "corpId": "ding1d7e8b088fab194f35c2f4657eb6378f", // 审批实例对应的企业
//     * "EventType": "bpms_task_change",   // 事件类型
//     * "businessId": "201908091058000275439",
//     * "title": "陈光跃提交的分包工程合同", // 实例标题
//     * "type": "finish", // 审批正常结束（同意或拒绝）的type为finish，审批终止的type为terminate
//     * "result": "agree",  // 正常结束时result为agree，拒绝时result为refuse，审批终止时没这个值
//     * "createTime": 1565319667000,  // 实例创建时间
//     * "processCode": "PROC-1DF9A928-FF53-4EB0-8D31-1E66AD3C1D28",  // 审批模板的唯一码
//     * "bizCategoryId": "",
//     * "staffId": "4258231637652962", // 发起审批实例的员工
//     * "taskId": 61920249919,
//     * "url": 'https://aflow.dingtalk.com/dingtalk/mobile/homepage.htm?corpid=ding2c015874d8175651&dd_share=', // 审批实例url，可在钉钉内跳转到审批页面
//     */
//
//    public void dispenseCallback(JSONObject obj) {
//
//        String processCode = obj.getString("processCode"); // 流程唯一嘛
//        String processTitle = obj.getString("title"); // 流程标题
//
//        if (processCode.equals(DingTalkConstant.PROCESSCODE_CONTRACT_ADD)) { // 合同新增流程/合同变更路程/合同作废流程
//
//            if(obj.getString("type").equals("start") && obj.containsKey("url")){
//                contractService.callbackAudit(1, obj.getString("url"), obj.getString("processInstanceId"));
//            }
//
//            if (obj.containsKey("result") && obj.getString("result").equals("agree")) { // result(agree, ,refuse)
//                //调用分发接口  修改审批状态为已完成， 并把审批流程 url 存储到对应合同中
//                contractService.callbackAudit(2, obj.getString("url"), obj.getString("processInstanceId"));
//            } else if ((obj.containsKey("result") && obj.getString("result").equals("refuse")) || obj.getString("type").equals("terminate")) { // 如果审批流程为驳回或终止，择把流程状态修改为驳回，并把流程url存储到对应合同
//                contractService.callbackAudit(9, obj.getString("url"), obj.getString("processInstanceId"));
//            }
//
//        } else if (processCode.equals(DingTalkConstant.PROCESSCODE_PAYMENT_ADD)) { // 付款审批流程回调
//
//            if(obj.getString("type").equals("start") && obj.containsKey("url")){
//                paymentService.callbackAudit(1, obj.getString("url"), obj.getString("processInstanceId"));
//            }
//
//            if (obj.containsKey("result") && obj.getString("result").equals("agree")) { // result(agree, ,refuse)
//                //调用分发接口  修改审批状态为已完成， 并把审批流程 url 存储到对应合同中
//                paymentService.callbackAudit(2, obj.getString("url"), obj.getString("processInstanceId"));
//            } else if ((obj.containsKey("result") && obj.getString("result").equals("refuse")) || obj.getString("type").equals("terminate")) { // 如果审批流程为驳回或终止，择把流程状态修改为驳回，并把流程url存储到对应合同
//                paymentService.callbackAudit(9, obj.getString("url"), obj.getString("processInstanceId"));
//            }
//        }
//
//
//    }
//
//    public static void main(String[] args) throws Exception {
//        // 先删除企业已有的回调
//        DingTalkClient client = new DefaultDingTalkClient(URLConstant.DELETE_CALLBACK);
//        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
//        request.setHttpMethod("GET");
//        client.execute(request, DingTalkUtil.getAccessToken());
//
//        // 重新为企业注册回调
//        client = new DefaultDingTalkClient(URLConstant.REGISTER_CALLBACK);
//        OapiCallBackRegisterCallBackRequest registerRequest = new OapiCallBackRegisterCallBackRequest();
//        registerRequest.setUrl(DingTalkConstant.CALLBACK_URL_HOST + "/callback");
//        registerRequest.setAesKey(ENCODING_AES_KEY);
//        registerRequest.setToken(TOKEN);
//        registerRequest.setCallBackTag(Arrays.asList("bpms_instance_change", "bpms_task_change"));
//        OapiCallBackRegisterCallBackResponse registerResponse = client.execute(registerRequest, DingTalkUtil.getAccessToken());
//        if (registerResponse.isSuccess()) {
//            System.out.println("回调注册成功了！！！");
//        }
//
//    }
//}
