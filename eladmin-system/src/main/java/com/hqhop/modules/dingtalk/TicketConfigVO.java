package com.hqhop.modules.dingtalk;

/**
 * 鉴权用到的数据对象
 */
public class TicketConfigVO {


//    agentId: conf.agentId, // 必填，微应用ID
//    corpId: conf.corpId, // 必填，企业ID
//    timeStamp: conf.timeStamp, // 必填，生成签名的时间戳
//    nonceStr: conf.nonceStr, // 必填，生成签名的随机串
//    signature: conf.signature, // 必填，签

//    private String ticket;

    private String signature;

    private String nonceStr;

    private Long timeStamp;

    private String corpId;

    private String agentId;

//    private


    public TicketConfigVO() {
    }

    public TicketConfigVO(String signature, String nonceStr, Long timeStamp, String corpId, String agentId) {
        this.signature = signature;
        this.nonceStr = nonceStr;
        this.timeStamp = timeStamp;
        this.corpId = corpId;
        this.agentId = agentId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}


