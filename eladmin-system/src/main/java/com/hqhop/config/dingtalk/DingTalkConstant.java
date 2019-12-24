package com.hqhop.config.dingtalk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
    public  static String PROCESSCODE_MATERIAL_ADD ;

    /** 物料生产档案管理审批 **/
    public  static String PROCESSCODE_MATERIALPRODUCTION_ADD ;

    @Value("${dingtalk.corpid}")
    public String corpid;

    @Value("${dingtalk.appkey}")
    public String appkey;

    @Value("${dingtalk.appsecret}")
    public String appsecret;

    @Value("${dingtalk.agentid}")
    public String agentid;

    @Value("${dingtalk.processcode.material-base-add}")
    public String processcodeMaterialBaseAdd;
    @Value("${dingtalk.processcode.material-base-add}")
    public String processcodeMaterialProductionAdd;

    @PostConstruct
    public void init() {
        CORPID = this.corpid;
        AGENTID = this.agentid;
        APPKEY = this.appkey;
        APPSECRET = this.appsecret;
        PROCESSCODE_MATERIAL_ADD = this.processcodeMaterialBaseAdd;
        PROCESSCODE_MATERIALPRODUCTION_ADD = this.processcodeMaterialProductionAdd;
    }
}
