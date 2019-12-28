package com.hqhop.modules.system.domain.dingVo.deptVo;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/27 0027 9:51
 * @description：钉钉部门对象
 * @modified By：
 * @version: $
 */
public class DingDeptCreateVo {

    //部门名称
    String name;

    //父部门ID
    String parentid;

    //在父部门中的排序值
    String order;

    //是否创建一个关联此部门的企业群
    Boolean createDeptGroup;

    //是否隐藏部门
    Boolean deptHiding;

    //可以查看指定隐藏部门的其他部门列表
    String deptPermits;

    //可以查看指定隐藏部门的其他人员列表
    String userPermits;

    //限制本部门成员查看通讯录
    Boolean outerDept;

    //配置额外可见部门
    String outerPermitDepts;

    //配置额外可见人员
    String outerPermitUsers;

    //是否只能看到所在部门及下级部门通讯录
    Boolean outerDeptOnlySelf;

    //部门标识字段
    String sourceIdentifier;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Boolean getCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(Boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }

    public Boolean getDeptHiding() {
        return deptHiding;
    }

    public void setDeptHiding(Boolean deptHiding) {
        this.deptHiding = deptHiding;
    }

    public String getDeptPermits() {
        return deptPermits;
    }

    public void setDeptPermits(String deptPermits) {
        this.deptPermits = deptPermits;
    }

    public String getUserPermits() {
        return userPermits;
    }

    public void setUserPermits(String userPermits) {
        this.userPermits = userPermits;
    }

    public Boolean getOuterDept() {
        return outerDept;
    }

    public void setOuterDept(Boolean outerDept) {
        this.outerDept = outerDept;
    }

    public String getOuterPermitDepts() {
        return outerPermitDepts;
    }

    public void setOuterPermitDepts(String outerPermitDepts) {
        this.outerPermitDepts = outerPermitDepts;
    }

    public String getOuterPermitUsers() {
        return outerPermitUsers;
    }

    public void setOuterPermitUsers(String outerPermitUsers) {
        this.outerPermitUsers = outerPermitUsers;
    }

    public Boolean getOuterDeptOnlySelf() {
        return outerDeptOnlySelf;
    }

    public void setOuterDeptOnlySelf(Boolean outerDeptOnlySelf) {
        this.outerDeptOnlySelf = outerDeptOnlySelf;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }
}
