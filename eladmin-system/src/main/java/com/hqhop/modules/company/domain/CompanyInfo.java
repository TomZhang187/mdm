package com.hqhop.modules.company.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author zf
* @date 2019-10-22
*/
@Entity
//@ToString(exclude={"contacts"})
@Table(name="company_info")
public class CompanyInfo implements Serializable {

    // 客商ID
    @Id
    @Column(name = "company_key")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long companyKey;

    // 批准时间
    @Column(name = "approve_time")
    private Timestamp approveTime;

    // 所属地区
    @Column(name = "belong_area",nullable = false)
    private Integer belongArea;

    // 所属公司
    @Column(name = "belong_company",nullable = false)
    private Integer belongCompany;

    // 公司属性
    @Column(name = "company_prop",nullable = false)
    private Integer companyProp;

    // 客商简称
    @Column(name = "company_short_name")
    private String companyShortName;

    // 客商状态
    @Column(name = "company_state",nullable = false)
    private Integer companyState;

    // 客商类型
    @Column(name = "company_type",nullable = false)
    private Integer companyType;

    // 客商名称
    @Column(name = "compay_name")
    private String compayName;

    // 通讯地址
    @Column(name = "contact_address")
    private String contactAddress;

    // 经济类型
    @Column(name = "economic_type",nullable = false)
    private Integer economicType;

    // 外文名称
    @Column(name = "foreign_name")
    private String foreignName;

    // 是否禁用
    @Column(name = "is_disable",nullable = false)
    private Integer isDisable;

    // 是否散户
    @Column(name = "is_retai",nullable = false)
    private Integer isRetai;

    // 法人
    @Column(name = "legalbody")
    private String legalbody;

    // 总公司编码
    @Column(name = "parent_company_id")
    private Long parentCompanyId;

    // 邮政编码
    @Column(name = "postal_code")
    private String postalCode;

    // 注册资金
    @Column(name = "registerfund")
    private BigDecimal registerfund;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 税务登记号
    @Column(name = "tax_id")
    private String taxId;

    // 所属行业
    @Column(name = "trade",nullable = false)
    private Integer trade;

    //联系人
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Contact.class ,cascade = CascadeType.ALL )
    @JoinColumn(name = "company_key")
    private Set<Contact > contacts = new HashSet<>();

    public void copy(CompanyInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public Long getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Long companyKey) {
        this.companyKey = companyKey;
    }

    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {



        this.approveTime = new Timestamp( new Date().getTime());;
    }

    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    public Integer getBelongCompany() {
        return belongCompany;
    }

    public void setBelongCompany(Integer belongCompany) {
        this.belongCompany = belongCompany;
    }

    public Integer getCompanyProp() {
        return companyProp;
    }

    public void setCompanyProp(Integer companyProp) {
        this.companyProp = companyProp;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public Integer getCompanyState() {
        return companyState;
    }

    public void setCompanyState(Integer companyState) {
        this.companyState = companyState;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getCompayName() {
        return compayName;
    }

    public void setCompayName(String compayName) {
        this.compayName = compayName;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Integer getEconomicType() {
        return economicType;
    }

    public void setEconomicType(Integer economicType) {
        this.economicType = economicType;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public Integer getIsRetai() {
        return isRetai;
    }

    public void setIsRetai(Integer isRetai) {
        this.isRetai = isRetai;
    }

    public String getLegalbody() {
        return legalbody;
    }

    public void setLegalbody(String legalbody) {
        this.legalbody = legalbody;
    }

    public Long getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(Long parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getRegisterfund() {
        return registerfund;
    }

    public void setRegisterfund(BigDecimal registerfund) {
        this.registerfund = registerfund;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Integer getTrade() {
        return trade;
    }

    public void setTrade(Integer trade) {
        this.trade = trade;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "companyKey=" + companyKey +
                ", approveTime=" + approveTime +
                ", belongArea=" + belongArea +
                ", belongCompany=" + belongCompany +
                ", companyProp=" + companyProp +
                ", companyShortName='" + companyShortName + '\'' +
                ", companyState=" + companyState +
                ", companyType=" + companyType +
                ", compayName='" + compayName + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", economicType=" + economicType +
                ", foreignName='" + foreignName + '\'' +
                ", isDisable=" + isDisable +
                ", isRetai=" + isRetai +
                ", legalbody='" + legalbody + '\'' +
                ", parentCompanyId=" + parentCompanyId +
                ", postalCode='" + postalCode + '\'' +
                ", registerfund=" + registerfund +
                ", remark='" + remark + '\'' +
                ", taxId='" + taxId + '\'' +
                ", trade=" + trade +
                ", contacts=" + contacts +
                '}';
    }
}