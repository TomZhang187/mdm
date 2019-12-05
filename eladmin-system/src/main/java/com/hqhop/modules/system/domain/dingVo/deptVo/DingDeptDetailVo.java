package com.hqhop.modules.system.domain.dingVo.deptVo;

import com.dingtalk.api.response.OapiDepartmentGetResponse;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/27 0027 15:46
 * @description：钉部门详情对象
 * @modified By：
 * @version: $
 */
public class DingDeptDetailVo {


    // 返回码
    Long errcode;

    //对返回码的文本描述内容
    String errmsg;

    //部门id
    Long id;

    //部门名称
    String name;

    //父部门id
    Long parentid;

   //部门标识字段
    String sourceIdentifier;

    //部门主管列表
    String deptManagerUseridList;

    public Long getErrcode() {
        return errcode;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList;
    }


    public void getDataByResponse( OapiDepartmentGetResponse response){
         this.errcode = response.getErrcode();
         this.errmsg = response.getErrmsg();
         this.id = response.getId();
         this.name = response.getName();
         this.parentid = response.getParentid();
         this.sourceIdentifier = response.getSourceIdentifier();
         this.deptManagerUseridList = response.getDeptManagerUseridList();
    }


}
