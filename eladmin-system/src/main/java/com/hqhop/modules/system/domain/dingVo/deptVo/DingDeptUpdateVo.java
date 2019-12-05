package com.hqhop.modules.system.domain.dingVo.deptVo;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/27 0027 10:15
 * @description：钉钉部门修改对象
 * @modified By：
 * @version: $
 */
public class DingDeptUpdateVo {


    //通讯录语言
    String lang="zh_CN";


    //部门名称
    String name;

    //父部门ID
    String parentid;

    //在父部门中的排序值
    String order;


    //部门id
    String id;

    //是否创建一个关联此部门的企业群
    Boolean createDeptGroup;


    //部门群是否包含子部门
    Boolean groupContainSubDept;

    //部门群是否包含外包部门
    Boolean groupContainOuterDept;

    //部门群是否包含隐藏部门
    Boolean groupContainHiddenDept;

    //如果有新人加入部门是否会自动加入部门群
    Boolean autoAddUser;

    //部门的主管列表
    String deptManagerUseridList;


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

    //企业群群主
    String orgDeptOwner;

    //部门标识字段
    String sourceIdentifier;


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(Boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }

    public Boolean getGroupContainSubDept() {
        return groupContainSubDept;
    }

    public void setGroupContainSubDept(Boolean groupContainSubDept) {
        this.groupContainSubDept = groupContainSubDept;
    }

    public Boolean getGroupContainOuterDept() {
        return groupContainOuterDept;
    }

    public void setGroupContainOuterDept(Boolean groupContainOuterDept) {
        this.groupContainOuterDept = groupContainOuterDept;
    }

    public Boolean getGroupContainHiddenDept() {
        return groupContainHiddenDept;
    }

    public void setGroupContainHiddenDept(Boolean groupContainHiddenDept) {
        this.groupContainHiddenDept = groupContainHiddenDept;
    }

    public Boolean getAutoAddUser() {
        return autoAddUser;
    }

    public void setAutoAddUser(Boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }

    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList;
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

    public String getOrgDeptOwner() {
        return orgDeptOwner;
    }

    public void setOrgDeptOwner(String orgDeptOwner) {
        this.orgDeptOwner = orgDeptOwner;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }
}
