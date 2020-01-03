package com.hqhop.modules.company.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
* @author zf
* @date 2019-11-07
*/
@Entity
@Table(name="company_update")
public class CompanyUpdate implements Serializable {

    // 操作记录主键
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "operate_key")
    private Long operateKey;

    // 审批实例ID
    @Column(name = "process_id")
    private String processId;


    // 审批结果
    @Column(name = "approve_result")
    private String approveResult="未知";

    // 操作人ID
    @Column(name = "user_id")
    private String userId;

    // 创建人
    @Column(name = "create_man")
    private String createMan;

    //创建时间
    @Column(name = "create_time")
    private Timestamp createTime;


    // 审批时间
    @Column(name = "approve_time")
    private Timestamp approveTime;



    // 操作类型
    @Column(name = "operation_type")
    private String  operationType;

    // 操作时间
    @Column(name = "operate_time")
    private Timestamp operateTime;

    // 公司主键
    @Column(name = "company_key")
    private Long companyKey;

    // 公司属性
    @Column(name = "company_prop")
    private String companyProp;

    // 公司简称
    @Column(name = "company_short_name")
    private String companyShortName;



    // 所属地区
    @Column(name = "belong_area")
    private String belongArea;

    // 所属公司
    @Column(name = "belong_company")
    private String belongCompany;


    // 公司状态
    @Column(name = "company_state")
    private String companyState="1";

    // 审批链接
    @Column(name = "ding_url")
    private String dingUrl;

    // 客商属性
    @Column(name = "customer_prop")
    private String customerProp;

    //客商类型
    @Column(name = "customer_type")
    private String customerType="0";



    // 公司名称
    @Column(name = "company_name")
    private String companyName;

    // 联系地址
    @Column(name = "contact_address")
    private String contactAddress;


    // 经济类型
    @Column(name = "economic_type")
    private String economicType;

    // 外文名称
    @Column(name = "foreign_name")
    private String foreignName;

    // 是否停用
    @Column(name = "is_disable")
    private String isDisable;

    // 是否散户
    @Column(name = "is_retai")
    private String isRetai="0";

    // 是否协同付款
    @Column(name = "is_synergy_pay")
        private String isSynergyPay="0";

    // 法人
    @Column(name = "legalbody")
    private String legalbody;


    // 总公司
    @Column(name = "parent_company_id")
    private Long parentCompanyId;

    // 邮政编码
    @Column(name = "postal_code")
    private String postalCode;


    // 字符注册资金
    @Column(name = "str_registerfund")
    private String strRegisterfund;

    // 注册资金（转companyinfo使用）
    private BigDecimal registerfund;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 纳税编号
    @Column(name = "tax_id")
    private String taxId;

    // 所属行业
    @Column(name = "trade")
    private String trade;

    //关联唯一的联系人记录
    @Column(name = "contact_key")
    private Long contactKey;

    //关联唯一的账户记录
    @Column(name = "account_key")
    private Long accountKey;



    public void copy(CompanyUpdate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }


    @Override
    public String toString() {
        return "CompanyUpdate{" +
                "operateKey=" + operateKey +
                ", processId='" + processId + '\'' +
                ", approveResult='" + approveResult + '\'' +
                ", userId='" + userId + '\'' +
                ", createMan='" + createMan + '\'' +
                ", approveTime=" + approveTime +
                ", operationType='" + operationType + '\'' +
                ", operateTime=" + operateTime +
                ", companyKey=" + companyKey +
                ", companyProp='" + companyProp + '\'' +
                ", companyShortName='" + companyShortName + '\'' +
                ", belongArea='" + belongArea + '\'' +
                ", belongCompany='" + belongCompany + '\'' +
                ", companyState='" + companyState + '\'' +
                ", dingUrl='" + dingUrl + '\'' +
                ", customerProp='" + customerProp + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", economicType='" + economicType + '\'' +
                ", foreignName='" + foreignName + '\'' +
                ", isDisable='" + isDisable + '\'' +
                ", isRetai='" + isRetai + '\'' +
                ", isSynergyPay='" + isSynergyPay + '\'' +
                ", legalbody='" + legalbody + '\'' +
                ", parentCompanyId=" + parentCompanyId +
                ", postalCode='" + postalCode + '\'' +
                ", strRegisterfund='" + strRegisterfund + '\'' +
                ", registerfund=" + registerfund +
                ", remark='" + remark + '\'' +
                ", taxId='" + taxId + '\'' +
                ", trade='" + trade + '\'' +
                ", contactKey=" + contactKey +
                ", accountKey=" + accountKey +
                '}';
    }

    public Long getContactKey() {
        return contactKey;
    }

    public void setContactKey(Long contactKey) {
        this.contactKey = contactKey;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }



    public void setCreateTime(Timestamp createTime) {
            this.createTime = createTime;
    }


    public String getDingUrl() {
        return dingUrl;
    }

    public void setDingUrl(String dingUrl) {
        this.dingUrl = dingUrl;
    }

    public Long getOperateKey() {
        return operateKey;
    }

    public void setOperateKey(Long operateKey) {
        this.operateKey = operateKey;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }


    public Timestamp getApproveTime() {
        return approveTime;
    }


    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }


    public Long getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(Long accountKey) {
        this.accountKey = accountKey;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getBelongArea() {
        return belongArea;
    }

    @JsonIgnore
    public Integer getBelongAreaInt() {
        return Integer.parseInt(belongArea);
    }

    public void setBelongArea(String belongArea) {
        this.belongArea = belongArea;
    }

    public String getBelongCompany() {
        return belongCompany;
    }



    public void setBelongCompany(String belongCompany) {
        this.belongCompany = belongCompany;
    }

    public Long getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Long companyKey) {
        this.companyKey = companyKey;
    }

    public String getCompanyProp() {
        return companyProp;
    }
    @JsonIgnore
    public Integer getCompanyPropInt() {
        return Integer.parseInt(companyProp);
    }

    public void setCompanyProp(String companyProp) {
        this.companyProp = companyProp;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyState() {
        return companyState;
    }
    @JsonIgnore
    public Integer getCompanyStateInt() {
        return Integer.parseInt(companyState);
    }

    public void setCompanyState(String companyState) {
        this.companyState = companyState;
    }

    public String getCustomerProp() {
        return customerProp;
    }

    public void setCustomerProp(String customerProp) {
        this.customerProp = customerProp;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getEconomicType() {
        return economicType;
    }
    @JsonIgnore
    public Integer getEconomicTypeInt() {
        return Integer.parseInt(economicType);
    }

    public void setEconomicType(String economicType) {
        this.economicType = economicType;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getIsDisable() {
        return isDisable;
    }
    @JsonIgnore
    public Integer getIsDisableInt() {
        return Integer.parseInt(isDisable);
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }

    public String getIsRetai() {
        return isRetai;
    }
    @JsonIgnore
    public Integer getIsRetaiInt() {

            return Integer.parseInt(isRetai);


    }

    public void setIsRetai(String isRetai) {
        this.isRetai = isRetai;
    }

    public String getIsSynergyPay() {
        return isSynergyPay;
    }
    @JsonIgnore
    public Integer getIsSynergyPayInt() {
        return Integer.parseInt(isSynergyPay);
    }

    public void setIsSynergyPay(String isSynergyPay) {
        this.isSynergyPay = isSynergyPay;
    }

    public String getLegalbody() {
        return legalbody;
    }

    public void setLegalbody(String legalbody) {
        this.legalbody = legalbody;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {

        this.operateTime = new Timestamp(operateTime.getTime());
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
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

    public String getTrade() {
        return trade;
    }
    @JsonIgnore
    public Integer getTradeInt() {
        return Integer.parseInt(trade);

    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStrRegisterfund() {
        return strRegisterfund;
    }

    public void setStrRegisterfund(String strRegisterfund) {
        this.strRegisterfund = strRegisterfund;
    }

    public CompanyInfo toCompanyInfo(){
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyKey(this.companyKey);
        companyInfo.setCreateMan(this.createMan);
//        companyInfo.setCreateTime(this.createTime);
        companyInfo.setBelongArea(this.belongArea);
        companyInfo.setBelongCompany(this.belongCompany);
        companyInfo.setCompanyProp(this.companyProp);
        companyInfo.setCompanyShortName(this.companyShortName);
        companyInfo.setCompanyState(Integer.parseInt(this.companyState));
        companyInfo.setCustomerProp(this.customerProp);
        companyInfo.setCustomerType(this.customerType);
        companyInfo.setCompanyName(this.companyName);
        companyInfo.setContactAddress(this.contactAddress);
        companyInfo.setEconomicType(this.economicType);
        companyInfo.setForeignName(this.foreignName);
        companyInfo.setIsDisable(Integer.parseInt(this.isDisable));
        companyInfo.setIsRetai(Integer.parseInt(this.isRetai));
        companyInfo.setLegalbody(this.legalbody);
        companyInfo.setParentCompanyId(this.parentCompanyId);
        companyInfo.setPostalCode(this.postalCode);
        companyInfo.setRegisterfund(this.registerfund);
        companyInfo.setRemark(this.remark);
        companyInfo.setTaxId(this.taxId);
        companyInfo.setTrade(this.trade);
        companyInfo.setIsSynergyPay(Integer.parseInt(this.isSynergyPay));
         return companyInfo;
    }

    public void copyCompanyInfo(CompanyInfo companyInfo){
        this.companyKey = companyInfo.getCompanyKey();
            this.createMan = companyInfo.getCreateMan();
        this.belongArea = companyInfo.getBelongArea();
       this.belongCompany = companyInfo.getBelongCompany();
       this.companyProp = companyInfo.getCompanyProp();
       this.companyShortName = companyInfo.getCompanyShortName();
       this.companyState = companyInfo.getCompanyState().toString();
       this.customerProp = companyInfo.getCustomerProp();
       this.customerType = companyInfo.getCustomerType();
       this.companyName = companyInfo.getCompanyName();
       this.contactAddress = companyInfo.getContactAddress();
       this.economicType = companyInfo.getEconomicType();
       this.foreignName = companyInfo.getForeignName();
       this.isDisable = companyInfo.getIsDisable().toString();
       this.isRetai = companyInfo.getIsRetai()!=null?companyInfo.getIsRetai().toString():null;
       this.isSynergyPay = companyInfo.getIsSynergyPay()!=null?companyInfo.getIsSynergyPay().toString():null;
       this.legalbody = companyInfo.getLegalbody();
       this.parentCompanyId = companyInfo.getParentCompanyId();
       this.postalCode = companyInfo.getPostalCode();
       this.registerfund = companyInfo.getRegisterfund();
       this.remark = companyInfo.getRemark();
       this.taxId = companyInfo.getTaxId();
       this.trade = companyInfo.getTrade();
    }

}