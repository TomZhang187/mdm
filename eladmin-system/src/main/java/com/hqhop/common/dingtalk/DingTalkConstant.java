package com.hqhop.common.dingtalk;

import org.springframework.context.annotation.Configuration;

/**
 * 定义钉钉常量
 * @author chengy
 */
@Configuration
public class DingTalkConstant {

    public static String CORPID;
    public static String AGENTID;
    public static String APPKEY ;
    public static String APPSECRET;

    /** 物料基本档案管理审批 **/
    public  static String PROCESSCODE_MATERIAL_ADD="PROC-B0A45D0A-A9FC-43AE-BD86-76F54B5902AA" ;

    /** 物料生产档案管理审批 **/
    public  static String PROCESSCODE_MATERIALPRODUCTION_ADD="PROC-7E6D638F-4D21-4441-AB53-71BB48B86223" ;

    public static String PROCESSCODE_CUSTOMER_MANAGE="PROC-0ECA9104-1CAC-476C-8934-3FE3FA69F1EE";


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
