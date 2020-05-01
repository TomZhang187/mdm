package com.hqhop.common.dingtalk;

import org.springframework.context.annotation.Configuration;

/**
 * 定义钉钉常量
 * @author chengy
 */
@Configuration
public class DingTalkConstant {

    public static String CORPID="ding872e051ff9bb057224f2f5cc6abecb85";
    public static String AGENTID="347498069";
    public static String APPKEY ="dingukv2uxqkycdnjqjt";
    public static String APPSECRET="fuvhSmxeYCKHH4kjL18LBJdVyAAKSAh_r1XQL_5K3W-GHdrJwiFzKOul5lpr_vIn";

    /**
     * 后台服务器的URL 外网地址，审批流程回调函数校验时需要用到
     */
    public static final String CALLBACK_URL_HOST = "http://lylcallback.vaiwan.com";
    /**

    /** 物料基本档案管理审批 **/
    public  static String PROCESSCODE_MATERIAL_ADD="PROC-B0A45D0A-A9FC-43AE-BD86-76F54B5902AA" ;

    /** 物料生产档案管理审批 **/
    public  static String PROCESSCODE_MATERIALPRODUCTION_ADD="PROC-7E6D638F-4D21-4441-AB53-71BB48B86223" ;
    /**
     *
     * 客商管理审批
     */
    public static String PROCESSCODE_CUSTOMER_MANAGE="PROC-38673FD5-511C-45D4-A9FE-097BD630BA28";

    /**
     * 客商测试审批
     */
    public static String PROCESSCODE_CUSTOMER_TEST="PROC-42E26442-C2E1-4630-87DD-E7D193F489E9";

//
//    @Value("${dingtalk.corpid}")
//    public String corpid;
//
//    @Value("${dingtalk.appkey}")
//    public String appkey;
//
//    @Value("${dingtalk.appsecret}")
//    public String appsecret;
//
//    @Value("${dingtalk.agentid}")
//    public String agentid;

//    @Value("${dingtalk.processcode.material-base-add}")
//    public String processcodeMaterialBaseAdd;
//    @Value("${dingtalk.processcode.material-base-add}")
//    public String processcodeMaterialProductionAdd;

//    @PostConstruct
//    public void init() {
//        CORPID = this.corpid;
//        AGENTID = this.agentid;
//        APPKEY = this.appkey;
//        APPSECRET = this.appsecret;
////        PROCESSCODE_MATERIAL_ADD = this.processcodeMaterialBaseAdd;
////        PROCESSCODE_MATERIALPRODUCTION_ADD = this.processcodeMaterialProductionAdd;
//    }
}
