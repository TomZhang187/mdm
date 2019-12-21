package com.hqhop.config.dingtalk;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * 定义钉钉常量
 * @author chengy
 */
public class DingTalkConstant {

    public static String CORPID;
    public static String AGENTID;
    public static String APPKEY ;
    public static String APPSECRET;

    @Value("${dingtalk.corpid}")
    public String corpid;

    @Value("${dingtalk.appkey}")
    public String appkey;

    @Value("${dingtalk.appsecret}")
    public String appsecret;

    @Value("${dingtalk.agentid}")
    public String agentid;

    @PostConstruct
    public void init() {
        this.CORPID = this.corpid;
        this.AGENTID = this.agentid;
        this.APPKEY = this.appkey;
        this.APPSECRET = this.appsecret;
    }

    /*
    * 物料基本档案管理审批
    * */
    public  static final String PROCESSCODE_MATERIAL_ADD = "PROC-B0A45D0A-A9FC-43AE-BD86-76F54B5902AA";

    /*
     * 物料生产档案管理审批
     * */
    public  static final String PROCESSCODE_MATERIALPRODUCTION_ADD = "PROC-B0A45D0A-A9FC-43AE-BD86-76F54B5902AA";

}
