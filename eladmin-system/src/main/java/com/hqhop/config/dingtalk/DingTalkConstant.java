package com.hqhop.config.dingtalk;

/**
 * 定义钉钉常量
 * @author chengy
 */
public class DingTalkConstant {

    public static final String CORPID = "ding1d7e8b088fab194f35c2f4657eb6378f";

    public static final String AGENTID = "310011589";
    public static final String APPKEY = "dingwatmyvu3ikfjra8v";
    public static final String APPSECRET = "JnZa4xFdrdIV_OQ8dxIsk4h6x8VSyG4DLyTcDwIeB6Cg_ghS4jbA2kE-mpf8ruUM";
    /**
     * 后台服务器的URL 外网地址，审批流程回调函数校验时需要用到
     */
    public static final String CALLBACK_URL_HOST = "http://company.vaiwan.com";
    /**
     * 前端请求的URL， 因钉钉鉴权时需要用到这个变量所有就把它配置到这里了
     */
    public static final String WEBURL = "http://chengy.vaiwan.com/";

    /**
     * 合同新增审批流processCode
     */
    public static final String PROCESSCODE_CONTRACT_ADD = "PROC-1DF9A928-FF53-4EB0-8D31-1E66AD3C1D28";

//    /**
//     * 合同变更审批流processCode
//     */
//    public static final String PROCESSCODE_CONTRACT_CHANGE = "PROC-1DF9A928-FF53-4EB0-8D31-1E66AD3C1D28";
//
//    /**
//     * 合同作废审批流程processCode
//     */
//    public static final String PROCESSCODE_CONTRACT_INVALID = "PROC-1DF9A928-FF53-4EB0-8D31-1E66AD3C1D28";

    /**
     * 付款单新增审批流程processCode
     */
    public static final String PROCESSCODE_PAYMENT_ADD = "PROC-7C4E26F5-AC8F-49A0-B9EB-D78987B71AEC";

//    /**
//     * 需要做回调数据处理的流程都放到这个集合中
//     */
//    public static final String[] PROCESSCODES = {PROCESSCODE_CONTRACT_ADD,PROCESSCODE_CONTRACT_CHANGE,PROCESSCODE_CONTRACT_INVALID,PROCESSCODE_PAYMENT_ADD};

    /*
       客商审批processCode
      * */
    public static final String PROCESSCODE_COMPAY_ADD = "PROC-1F000E3F-6764-4631-8712-6E270120E039";



    /*
    * 物料审批processCode
    * */
    public  static final String PROCESSCODE_MATERIAL_ADD = " PROC-7E6D638F-4D21-4441-AB53-71BB48B86223";




}
