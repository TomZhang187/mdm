package com.hqhop.modules.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.taobao.api.internal.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;


public class DingTalkUtils {


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
     * 获取用户详细信息
     *
     * @param userid
     * @return
     * @throws ApiException
     */
//    public static ResultVO getUserInfo(String userid) throws ApiException {
//
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
//        OapiUserGetRequest request = new OapiUserGetRequest();
//        request.setUserid(userid);
//        request.setHttpMethod("GET");
//        OapiUserGetResponse response = null;
//        response = client.execute(request, getAccessToken());
//        response.setBody(null);
//        return ResultUtil.success(response);
//    }

    /**
     * 获取企业下的钉盘自定义空间
     *
     * @return
     * @throws ApiException 工程合同 spaceid = 1771283395
     */
    public static OapiCspaceGetCustomSpaceResponse getCustomSpace() throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/get_custom_space");
        OapiCspaceGetCustomSpaceRequest request = new OapiCspaceGetCustomSpaceRequest();
        request.setAgentId(DingTalkConstant.AGENTID);
        request.setDomain("contract");
        request.setHttpMethod("GET");
        OapiCspaceGetCustomSpaceResponse response = client.execute(request, getAccessToken());

        return response;
    }

    /**
     * 获取审批流的附件空间
     *
     * @param userid
     * @return
     * @throws ApiException
     */
    public static OapiProcessinstanceCspaceInfoResponse getProcessSpace(String userid) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/cspace/info");
        OapiProcessinstanceCspaceInfoRequest req = new OapiProcessinstanceCspaceInfoRequest();
        req.setUserId(userid);
        OapiProcessinstanceCspaceInfoResponse rsp = client.execute(req, getAccessToken());
        System.out.println(rsp.getBody());
        return rsp;
    }


    /**
     * 授权用户 上传和下载自定义空间权限 默认为/contract路径
     * <p>
     * https://oapi.dingtalk.com/cspace/grant_custom_space?access_token=ACCESS_TOKEN&domain=preview&type=download&userid=USERID&fileids=xxx,yyy
     *
     * @param userid
     * @param type   add  表示删除权限  download 表示下载权限
     * @return
     * @throws ApiException
     */
    public static OapiCspaceGrantCustomSpaceResponse grantCustomSpace(String userid, String type, String fileids) throws ApiException {

        OapiCspaceGetCustomSpaceResponse customSpace = getCustomSpace();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/grant_custom_space");
        OapiCspaceGrantCustomSpaceRequest request = new OapiCspaceGrantCustomSpaceRequest();
        request.setAgentId(DingTalkConstant.AGENTID);
        request.setDomain("contract"); // 企业内部调用时传入，授权访问该domain的自定义空间
        request.setType(type); // 权限类型，目前支持上传和下载，上传请传add，下载请传download
        request.setUserid(userid); // 企业用户userid
        request.setPath("/"); // 授权访问的路径，如授权访问所有文件传"/"，授权访问/doc文件夹传"/doc/"，需要utf-8 urlEncode, type=add时必须传递
        request.setDuration(3600L); // 权限有效时间，有效范围为0~3600秒
        request.setHttpMethod("GET");
        if ("download".equals(type)) {
            request.setFileids(fileids);
        }
        OapiCspaceGrantCustomSpaceResponse response = client.execute(request, getAccessToken());

//        if(response.getErrcode() == 0 ){
//            ResultUtil.success();
//        } else {
//            new CustomException(response.getErrmsg());
//        }
        return response;
    }


    /**
     * 获取签名 Ticket
     *
     * @return
     * @throws ApiException
     */
    public static String getJsapiTicket() throws ApiException {

        long curTime = System.currentTimeMillis();

        if (jsTicket == null || curTime - startTimeTicket <= cacheTime) {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
            OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
            req.setTopHttpMethod("GET");
            OapiGetJsapiTicketResponse execute = client.execute(req, getAccessToken());
            jsTicket = execute.getTicket();
        }
        return jsTicket;
    }

    public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws DingTalkEncryptException {
        return DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);

    }

    /**
     * 计算当前请求的jsapi的签名数据<br/>
     * <p>
     * 如果签名数据是通过ajax异步请求的话，签名计算中的url必须是给用户展示页面的url
     *
     * @param request
     * @return
     */
    public static TicketConfigVO getConfig(HttpServletRequest request) throws ApiException {
//        String url = "http://chengy.vaiwan.com/";
        // 直接从配置文件中取， 因使用的时前端路由，对鉴权来说路径没有变所以就写死再这里了
        String url = DingTalkConstant.WEBURL;

//        String urlString = request.getRequestURL().toString();
//        String queryString = request.getQueryString();
//
//        String queryStringEncode = null;
//        String url;
//        if (queryString != null) {
//            queryStringEncode = URLDecoder.decode(queryString);
//            url = urlString + "?" + queryStringEncode;
//        } else {
//            url = urlString;
//        }

//        // 确认url与配置的应用首页地址一致
        System.out.println(url);
        /** * 随机字符串 */
        String nonceStr = "hqhop123456";
        long timeStamp = System.currentTimeMillis() / 1000;
        String signedUrl = url;
        String ticket = null;
        String signature = null;
        try {
            ticket = DingTalkUtils.getJsapiTicket();
            signature = DingTalkUtils.sign(ticket, nonceStr, timeStamp, signedUrl);

        } catch (ApiException | DingTalkEncryptException e) {
            e.printStackTrace();
        }

        return new TicketConfigVO(signature, nonceStr, timeStamp, DingTalkConstant.CORPID, DingTalkConstant.AGENTID);
//        return new TicketConfigVO(signature, timeStamp, DingTalkConstant.CORPID, DingTalkConstant.AGENTID);
    }


    public static OapiCallBackRegisterCallBackResponse callBack(String url) throws ApiException {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
//      request.setUrl("http://test001.vaiwan.com/eventreceive");
        // 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
        request.setUrl(url);
        // 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
        request.setAesKey("5678901234567dsf890123456789012345678901223");
        // 加解密需要用到的token，ISV(服务提供商)推荐使用注册套件时填写的token，普通企业可以随机填写
        request.setToken("12x345x6");
        // bpms_instance_change 审批实例开始，结束事件
        request.setCallBackTag(Arrays.asList("bpms_instance_change")); // 需要监听的事件类型
        OapiCallBackRegisterCallBackResponse response = client.execute(request, getAccessToken());
        response.isSuccess();
//        response.

        return response;
    }


    public static OapiFileUploadSingleResponse uploadSingle() throws ApiException, IOException {
        OapiFileUploadSingleRequest request = new OapiFileUploadSingleRequest();
        request.setFileSize(1000L);
        request.setAgentId(DingTalkConstant.AGENTID);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/file/upload/single?" + WebUtils.buildQuery(request.getTextParams(), "utf-8"));
// 必须重新new一个请求
        request = new OapiFileUploadSingleRequest();
        request.setFile(new FileItem("/Users/mxh/Downloads/test.png"));
        OapiFileUploadSingleResponse response = client.execute(request, accessToken);
        return response;
    }


    public static OapiProcessinstanceGetResponse getProcessInfo(String processsInstanceId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
        OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
        request.setProcessInstanceId(processsInstanceId);
        OapiProcessinstanceGetResponse response = client.execute(request, getAccessToken());

        return response;
    }

    public static void main(String[] args) {

    }
}
