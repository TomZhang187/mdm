package com.hqhop.common.dingtalk.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.oapi.lib.aes.Utils;
import com.hqhop.aop.log.Log;
import com.hqhop.common.dingtalk.DingTalkConstant;
import com.hqhop.common.dingtalk.DingTalkUtils;
import com.hqhop.common.dingtalk.URLConstant;
import com.hqhop.common.dingtalk.dingtalkVo.Approve;
import com.hqhop.modules.company.rest.CompanyCallbackController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Api(tags = "钉钉企业回调注册回调")
@RestController
@RequestMapping("ding")
public class RegisterCallBack {


    private static final String TOKEN = "LIANG_FENG";

    private static final String ENCODING_AES_KEY = "234eeglba9194j443343k4jh3j453453452dsfsdfsd";

    /**
     * 相应钉钉回调时的值
     */
    private static final String CALLBACK_RESPONSE_SUCCESS = "success";


    @Autowired
   private CompanyCallbackController companyCallbackController;





    @Log("修改CompanyInfo")
    @ApiOperation(value = "修改CompanyInfo")
    @PutMapping(value = "/testcallback")
    public ResponseEntity update() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> callback(@RequestParam(value = "signature", required = false) String signature,
                                        @RequestParam(value = "timestamp", required = false) String timestamp,
                                        @RequestParam(value = "nonce", required = false) String nonce,
                                        @RequestBody(required = false) JSONObject json) {
        String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;

        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(TOKEN, ENCODING_AES_KEY,
                    DingTalkConstant.CORPID);

            //从post请求的body中获取回调信息的加密数据进行解密处理
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            JSONObject obj = JSON.parseObject(plainText);
            Approve approve = new Approve(
                    obj.getString("EventType"),
                    obj.getString("processInstanceId"),
                    obj.getString("corpId"),
                    obj.getTimestamp("createTime"),
                    obj.getTimestamp("finishTime"),
                    obj.getString("title"),
                    obj.getString("type"),
                    obj.getString("staffId"),
                    obj.getString("url"),
                    obj.getString("remark"),
                    obj.getString("result"),
                    obj.getString("processCode")
            );
            System.out.println("---------钉钉回调---------"+"类型："+approve.getType()+"  审批实例ID:"+approve.getProcessInstanceId()+"结果："+approve.getResult());
            companyCallbackController.addCompanyCallback(approve);

            // 返回success的加密信息表示回调处理成功
            return dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(), Utils.getRandomStr(8));
        } catch (Exception e) {

            return null;
        }
    }

//    /**
//     * 注册回调接口
//     */
//    public  void  registerInterface() throws ApiException {
//
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
//        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
//        request.setUrl("http://test001.vaiwan.com/eventreceive");  //接收回调的网址
//        request.setAesKey(ENCODING_AES_KEY);
//        request.setToken(TOKEN);     //加密token 普通企业随便填
//        request.setCallBackTag(Arrays.asList("bpms_instance_change"));  //监听的审批事件类型
//        OapiCallBackRegisterCallBackResponse response = client.execute(request,DingTalkUtils.getAccessToken());
//        System.out.println("");
//
//
//
//}


        public static void main (String[]args) throws Exception {
            // 先删除企业已有的回调
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.DELETE_CALLBACK);
//            OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
//            request.setHttpMethod("GET");
//            System.out.println("accessToken:"+DingTalkUtils.getAccessToken());
//            try {
//                client.execute(request, DingTalkUtils.getAccessToken());
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }

            // 重新为企业注册回调
            client = new DefaultDingTalkClient(URLConstant.REGISTER_CALLBACK);
            OapiCallBackRegisterCallBackRequest registerRequest = new OapiCallBackRegisterCallBackRequest();
            registerRequest.setUrl(DingTalkConstant.CALLBACK_URL_HOST + "/ding/callback");
            registerRequest.setAesKey(ENCODING_AES_KEY);
            registerRequest.setToken(TOKEN);
            registerRequest.setCallBackTag(Arrays.asList("bpms_task_change"));
            System.out.println("accessToken:"+DingTalkUtils.getAccessToken());
            OapiCallBackRegisterCallBackResponse registerResponse = client.execute(registerRequest, DingTalkUtils.getAccessToken());
            if (registerResponse.isSuccess()) {
                System.out.println("回调注册成功了！！！");

            }

        }




}





