package com.hqhop.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Entity
@Table(name="dept")
public class Dept implements Serializable {

    /**
     * 部门主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;


    /*
    部门编码
    * */
    @Column(name = "dept_code")
    private String deptCode;

    /*
    钉钉对应部门ID
    * */
    @Column(name = "ding_id")
    private String dingId;

    /**
     * 名称
     */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;



    //部门简称
    @Column(name = "short_name")
    private String shortName;

    //显示顺序
    @Column(name = "show_order")
    private Long showOrder;


    //部门职责属性
    @Column(name = "dept_duty_and_property")
    private String deptDutyAndProperty;

    //部门属性
    @Column(name = "dept_property")
    private String  deptProperty;

    //助记码
    @Column(name = "mnemonic_code")
    private String mnemonicCode;

    //部门类型
    @Column(name = "dept_type")
    private String deptType;

    //成立时间
    @Column(name = "found_time")
    private Timestamp foundTime;

    //库存组织
    @Column(name = "inventory_organization")
    private String inventoryOrganization;

    //部门级别
    @Column(name = "dept_level")
    private String deptLevel;

    //电话
    @Column(name = "dept_phone")
    private String deptPhone;

    //部门主管列表
    @Column(name = "dept_manager_userid_list")
    private String  deptManagerUseridList;


    //是否零售
    @Column(name = "is_retail")
    private Integer isRetail;


    //备注
    @Column(name = "remark")
    private String remark;

    //地址
    @Column(name = "address")
    private String address;


    /*
     * 是否封存/启用
     * */
    @NotNull
    @Column(name = "enabled")
    private Boolean enabled;

    //部门封存时间
    @Column(name = "seal_time")
    private Timestamp sealTime;

    /**
     * 上级部门
     */
    @Column(name = "pid",nullable = false)
    @NotNull
    private Long pid;

    @JsonIgnore
    @ManyToMany(mappedBy = "depts")
    private Set<Role> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "depts")
    private Set<Employee> employees;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {}


    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        String [] str = deptManagerUseridList.split(",");
        String strDept = "";
       for(int i=0;i<str.length-1;i++){
           strDept +=str[i]+"|";
       }
        strDept += str[str.length-1];

        this.deptManagerUseridList =  strDept;
    }

    public void setDeptManagerUseridList(Map<String,Object> deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList.toString();
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", deptCode='" + deptCode + '\'' +
                ", dingId='" + dingId + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", showOrder=" + showOrder +
                ", deptDutyAndProperty='" + deptDutyAndProperty + '\'' +
                ", deptProperty='" + deptProperty + '\'' +
                ", mnemonicCode='" + mnemonicCode + '\'' +
                ", deptType='" + deptType + '\'' +
                ", foundTime=" + foundTime +
                ", inventoryOrganization='" + inventoryOrganization + '\'' +
                ", deptLevel='" + deptLevel + '\'' +
                ", deptPhone='" + deptPhone + '\'' +
                ", deptManagerUseridList='" + deptManagerUseridList + '\'' +
                ", isRetail=" + isRetail +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                ", enabled=" + enabled +
                ", sealTime=" + sealTime +
                ", pid=" + pid +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDingId() {
        return dingId;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Long showOrder) {
        this.showOrder = showOrder;
    }

    public String getDeptDutyAndProperty() {
        return deptDutyAndProperty;
    }

    public void setDeptDutyAndProperty(String deptDutyAndProperty) {
        this.deptDutyAndProperty = deptDutyAndProperty;
    }

    public String getDeptProperty() {
        return deptProperty;
    }

    public void setDeptProperty(String deptProperty) {
        this.deptProperty = deptProperty;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public Timestamp getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Timestamp foundTime) {
        this.foundTime = foundTime;
    }

    public String getInventoryOrganization() {
        return inventoryOrganization;
    }

    public void setInventoryOrganization(String inventoryOrganization) {
        this.inventoryOrganization = inventoryOrganization;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public Integer getIsRetail() {
        return isRetail;
    }

    public void setIsRetail(Integer isRetail) {
        this.isRetail = isRetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Timestamp getSealTime() {
        return sealTime;
    }

    public void setSealTime(Timestamp sealTime) {
        this.sealTime = sealTime;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
