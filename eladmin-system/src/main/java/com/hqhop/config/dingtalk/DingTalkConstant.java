package com.hqhop.config.dingtalk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 定义钉钉常量
 * @author chengy
 */
@Component
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
    public  static final String PROCESSCODE_MATERIALPRODUCTION_ADD = "PROC-7E6D638F-4D21-4441-AB53-71BB48B86223";

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }
}
