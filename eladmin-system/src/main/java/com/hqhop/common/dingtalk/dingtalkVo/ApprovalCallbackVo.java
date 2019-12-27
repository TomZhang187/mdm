package com.hqhop.common.dingtalk.dingtalkVo;

import cn.hutool.json.JSONObject;


import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/24 0024 16:22
 * @description：钉钉回调对象
 * @modified By：
 * @version: $
 */
public class ApprovalCallbackVo {

    //事件id
    public Long id;

       //事件类型
    public String eventType;

    //审批实例id
    public String processInstanceId;

    //审批实例对应的企业
    public String corpId;

    //实例创建时间
    public Timestamp createTime;

    //实例标题
    public String title;

    //类型
    public String type;

    //发起审批实例的员工
    public String staffId;

    //审批实例url
    public String url;

    //审批模板的唯一码
    public String processCode;

    //结束结果
    public String result;

    //审批结束时间
    public Timestamp finishTime;

    //表示操作时写的评论内容
    public String remark;


    public ApprovalCallbackVo() {
    }

    @Override
    public String toString() {
        return "ApprovalCallbackVo{" +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", corpId='" + corpId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", staffId='" + staffId + '\'' +
                ", url='" + url + '\'' +
                ", processCode='" + processCode + '\'' +
                ", result='" + result + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        if(createTime != null ){
            this.createTime = new Timestamp(createTime.getTime());
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getResult() {
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        if(finishTime != null){
            this.finishTime = new Timestamp(finishTime.getTime());
        }

    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public  static  ApprovalCallbackVo getVoByJSON(JSONObject jsonObject){

        ApprovalCallbackVo approvalCallbackVo = new ApprovalCallbackVo();

        approvalCallbackVo.setId(jsonObject.getLong("id"));

       approvalCallbackVo.setEventType(jsonObject.getStr("eventType"));

        approvalCallbackVo.setProcessInstanceId(jsonObject.getStr("processInstanceId"));

        approvalCallbackVo.setCorpId(jsonObject.getStr("corpId"));

        approvalCallbackVo.setCreateTime(jsonObject.getDate("createTime"));

        approvalCallbackVo.setTitle(jsonObject.getStr("title"));

        approvalCallbackVo.setType(jsonObject.getStr("type"));

        approvalCallbackVo.setStaffId(jsonObject.getStr("staffId"));

        approvalCallbackVo.setUrl(jsonObject.getStr("url"));

        approvalCallbackVo.setProcessCode(jsonObject.getStr("processCode"));

        approvalCallbackVo.setResult(jsonObject.getStr("result"));

        approvalCallbackVo.setFinishTime(jsonObject.getDate("finishTime"));

        approvalCallbackVo.setRemark(jsonObject.getStr("remark"));

        return  approvalCallbackVo;
    }
}
