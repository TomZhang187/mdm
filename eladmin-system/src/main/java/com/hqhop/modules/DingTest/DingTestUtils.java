package com.hqhop.modules.DingTest;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.hqhop.modules.dingtalk.DingTalkConstant;
import com.hqhop.modules.dingtalk.DingTalkUtils;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DingTestUtils {

    private static final Logger logger = LoggerFactory.getLogger(DingTalkUtils.class);

    private static long startTimeToken = 0;
    private static long startTimeTicket = 0;

    private static String accessToken = null;
    private static String jsTicket = null;

    /**
     * 调整到1小时50分钟
     */
    public static final long cacheTime = 1000 * 60 * 55 * 2;


    /**
     * 在此方法中，为了避免频繁获取access_token，
     * 在距离上一次获取access_token时间在两个小时之内的情况，
     * 将直接从持久化存储中读取access_token
     * <p>
     * 因为access_token和jsapi_ticket的过期时间都是7200秒
     * 所以在获取access_token的同时也去获取了jsapi_ticket
     * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
     * 具体信息请查看开发者文档--权限验证配置
     *
     * @return
     * @throws ApiException
     */
    public static String getAccessToken() throws ApiException {
        long curTime = System.currentTimeMillis();

        if (accessToken == null && curTime - startTimeToken >= cacheTime) {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(DingTalkConstant.APPKEY);
            request.setAppsecret(DingTalkConstant.APPSECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            accessToken = response.getAccessToken();
            startTimeToken = curTime;
        }
        System.out.println("========accessToken:" + accessToken + "========");
        return accessToken;
    }

    /**
     * 获取用户ID   //4258231637652962
     *
     * @param requestAuthCode
     * @return
     * @throws ApiException
     * @throws ApiException
     */
    public static String getUserId(String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = null;
        response = client.execute(request, getAccessToken());
        return response.getUserid();
    }

    /**
     * 发起审批实例
     *
     */

     public void  Approval() throws ApiException{

         DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");  //钉钉客户端


         OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();  //工作流的审批对象

         request.setAgentId(41605932L);    //设置企业应用标识
         request.setProcessCode("PROC-BY6LI83V-4R8T1CHNUX58O0Z6C55M3-DTQJJGEJ-1");
        // 审批流表单参数，设置各表单项值
         List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();

         // 单行输入框
         OapiProcessinstanceCreateRequest.FormComponentValueVo vo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         vo1.setName("单行输入框示例");
         vo1.setValue("单行输入框value");

        // 多行输入框
         OapiProcessinstanceCreateRequest.FormComponentValueVo vo2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         vo2.setName("多行输入框示例");
         vo2.setValue("多行输入框value");

        // 图片
         OapiProcessinstanceCreateRequest.FormComponentValueVo vo3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         vo3.setName("图片示例");
         vo3.setValue("[\"http://xxxxx\"]");

            // 明细包含控件
            // 明细-单行输入框
         OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         ItemName1.setName("明细-单行输入框示例");
         ItemName1.setValue("明细-单行输入框value");
        // 明细-多行输入框
         OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         ItemName2.setName("明细-多行输入框示例");
         ItemName2.setValue("明细-多行输入框value");
        // 明细-照片
         OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         ItemName3.setName("明细-图片示例");
         ItemName3.setValue("[\"http://xxxxx\"]");

            // 明细
         OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         vo4.setName("明细示例");
         vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3))));

            // 附件
         OapiProcessinstanceCreateRequest.FormComponentValueVo attachmentComponent = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
         JSONObject attachmentJson = new JSONObject();
         attachmentJson.put("fileId", "6433971140");
         attachmentJson.put("fileName", "2644.JPG");
         attachmentJson.put("fileType", "jpg");
         attachmentJson.put("spaceId", "1635477658");
         attachmentJson.put("fileSize", "333");

         JSONArray array = new JSONArray();
         array.add(attachmentJson);
         attachmentComponent.setValue(array.toJSONString());
         attachmentComponent.setName("附件");

         // 添加单行输入框、多行输入框、图片、明细、附件到表单
         formComponentValues.add(vo1);
         formComponentValues.add(vo2);
         formComponentValues.add(vo3);
         formComponentValues.add(vo4);
         formComponentValues.add(attachmentComponent);

         request.setFormComponentValues(formComponentValues);
         request.setApprovers("userid1,userid2");    //审批人id列表
         request.setOriginatorUserId("userid1");
         request.setDeptId(-1L);                       //发起人所在部门
         request.setCcList("userid1,userid2");       //抄送人idl列表
         request.setCcPosition("START_FINISH");         //抄送时间
         OapiProcessinstanceCreateResponse response = client.execute(request,accessToken);

     }
























}
