    package com.hqhop.modules.company.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.hqhop.modules.company.service.dto.CompanyDictDto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zf
 * @date 2019-10-22
 */
@Entity
//@ToString(exclude={"contacts"})
@Table(name = "company_info")
public class CompanyInfo implements Serializable {

    // 客商ID
    @Id
    @Column(name = "company_key")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long companyKey;

    //创建人
    @Column(name = "create_man")
    private String createMan;

    //创建时间
    @Column(name = " create_time")
    private Timestamp createTime;


    // 批准时间
    @Column(name = "approve_time")
    private Timestamp approveTime;

    //修改人
    @Column(name = "update_man")
    private String updateMan;


    //修改时间
    @Column(name = "update_time")
    private Timestamp updateTime;

    // 所属地区
    @Column(name = "belong_area")
    private String belongArea;

    // 所属公司
    @Column(name = "belong_company")
    private String belongCompany;

    // 公司属性
    @Column(name = "company_prop")
    private String companyProp;

    // 客商简称
    @Column(name = "company_short_name")
    private String companyShortName;

    // 客商状态
    @Column(name = "company_state", nullable = false)
    private Integer companyState;

    // 客商属性
    @Column(name = "customer_prop")
    private String customerProp;

    //客商类型
    @Column(name = "customer_type")
    private String customerType="0";

    // 客商名称
    @Column(name = "company_name")
    private String companyName;

    // 通讯地址
    @Column(name = "contact_address")
    private String contactAddress;

    // 经济类型
    @Column(name = "economic_type")
    private String economicType;

    // 外文名称
    @Column(name = "foreign_name")
    private String foreignName;

    // 是否禁用
    @Column(name = "is_disable")
    private Integer isDisable;

    // 是否同步到了U8c
    @Column(name = "is_sync_u8c")
    private Integer isSyncU8c;

    // 法人
    @Column(name = "legalbody")
    private String legalbody;

    // 总公司编码
    @Column(name = "parent_company_id")
    private Long parentCompanyId=0L;

    // 邮政编码
    @Column(name = "postal_code")
    private String postalCode;

    // 注册资金
    @Column(name = "registerfund")
    private BigDecimal registerfund;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 税务登记号 /客商编码
    @Column(name = "tax_id")
    private String taxId;

    // 所属行业
    @Column(name = "trade")
    private String trade;



    //是否协同付款
    @Column(name = "is_synergy_pay")
    private Integer isSynergyPay;

    //账户
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Account.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_key")
    private Set<Account > accounts  = new HashSet<>();


    //联系人
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Contact.class, cascade = CascadeType.PERSIST )
    @JoinColumn(name = "company_key")
    private Set<Contact> contacts = new HashSet<>();


    //专管部门
    @Column(name = "charge_department")
    private String chargeDepartment;

    //专管业务员
    @Column(name = "profession_salesman")
    private String professionSalesman;

   //信用等级
   @Column(name = "credit_rating")
    private String creditRating;

    //默认收款协议
    @Column(name = "defaulet_payment_agreement")
    private String defaultPaymentAgreement;


    @Override
    public String toString() {
        return "CompanyInfo{" +
                "companyKey=" + companyKey +
                ", createMan='" + createMan + '\'' +
                ", createTime=" + createTime +
                ", approveTime=" + approveTime +
                ", updateMan='" + updateMan + '\'' +
                ", updateTime=" + updateTime +
                ", belongArea='" + belongArea + '\'' +
                ", belongCompany='" + belongCompany + '\'' +
                ", companyProp='" + companyProp + '\'' +
                ", companyShortName='" + companyShortName + '\'' +
                ", companyState=" + companyState +
                ", customerType='" + customerType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", economicType='" + economicType + '\'' +
                ", foreignName='" + foreignName + '\'' +
                ", isDisable=" + isDisable +
                ", legalbody='" + legalbody + '\'' +
                ", parentCompanyId=" + parentCompanyId +
                ", postalCode='" + postalCode + '\'' +
                ", registerfund=" + registerfund +
                ", remark='" + remark + '\'' +
                ", taxId='" + taxId + '\'' +
                ", trade='" + trade + '\'' +
                ", isSynergyPay=" + isSynergyPay +
                ", chargeDepartment='" + chargeDepartment + '\'' +
                ", professionSalesman='" + professionSalesman + '\'' +
                ", defaultPaymentAgreement='" + defaultPaymentAgreement + '\'' +
                '}';
    }

    //添加账户方法
    public void addAccount(Account account){
        if(account != null){
            accounts.add(account);
        }
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public void copy(CompanyInfo source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getChargeDepartment() {
        return chargeDepartment;
    }

    public void setChargeDepartment(String chargeDepartment) {
        this.chargeDepartment = chargeDepartment;
    }

    public String getProfessionSalesman() {
        return professionSalesman;
    }

    public void setProfessionSalesman(String professionSalesman) {
        this.professionSalesman = professionSalesman;
    }

    public String getDefaultPaymentAgreement() {
        return defaultPaymentAgreement;
    }

    public void setDefaultPaymentAgreement(String defaultPaymentAgreement) {
        this.defaultPaymentAgreement = defaultPaymentAgreement;
    }

    public Long getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Long companyKey) {
        this.companyKey = companyKey;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //入参
    public void setCreateTime(Timestamp  createTime) {
        if(createTime != null){
            this.createTime = createTime;
        }

    }

    public Timestamp getApproveTime() {
        return approveTime;
    }


    public String getBelongArea() {
        return belongArea;
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

    public String getCompanyProp() {
        return companyProp;
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

    public Integer getCompanyState() {
        return companyState;
    }

    public void setCompanyState(Integer companyState) {
        this.companyState = companyState;
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

    public String getEconomicType() {
        return economicType;
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

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public Integer getIsSyncU8c() {
        return isSyncU8c;
    }

    public void setIsSyncU8c(Integer isSyncU8c) {
        this.isSyncU8c = isSyncU8c;
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

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {

        this.updateTime = updateTime;
    }

    public Integer getIsSynergyPay() {
        return isSynergyPay;
    }

    public void setIsSynergyPay(Integer isSynergyPay) {
        this.isSynergyPay = isSynergyPay;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }


    public String getCustomerProp() {
        return customerProp;
    }

    public void setCustomerProp(String customerProp) {
        this.customerProp = customerProp;
    }



    public void getBasicAttribute (CompanyInfo resources){
        this.createMan = resources.getCreateMan();
        this.createTime = resources.getCreateTime();
        this.approveTime = resources.getApproveTime();
        this.updateMan = resources.getUpdateMan();
        this.updateTime = resources.getUpdateTime();
        this.belongArea = resources.getBelongArea();
        this.belongCompany = resources.getBelongCompany();
        this.companyProp = resources.getCompanyProp();
        this.companyShortName = resources.getCompanyShortName();
        this.companyState = resources.getCompanyState();

        this.customerProp = resources.getCustomerProp();

        this.customerType = resources.getCustomerType();
        this.companyName = resources.getCompanyName();
        this.contactAddress = resources.getContactAddress();
        this.economicType = resources.getEconomicType();
        this.foreignName = resources.getForeignName();
        this.isDisable = resources.getIsDisable();
        this.isSyncU8c = resources.getIsSyncU8c();
        this.legalbody = resources.getLegalbody();
        this.parentCompanyId = resources.getParentCompanyId();
        this.postalCode = resources.getPostalCode();
        this.registerfund = resources.getRegisterfund();
        this.remark = resources.getRemark();
        this.taxId = resources.getTaxId();
        this.trade = resources.getTrade();
        this.isSynergyPay = resources.getIsSynergyPay();
//        this.accounts = accounts;
//        this.contacts = contacts;
        this.chargeDepartment = resources.getChargeDepartment();
        this.professionSalesman = resources.getProfessionSalesman();
        this.creditRating = resources.getCreditRating();
        this.defaultPaymentAgreement = resources.getDefaultPaymentAgreement();
    }


public CompanyDictDto getDictDto(){
        CompanyDictDto companyDictDto = new CompanyDictDto();
        companyDictDto.setCompanyKey(this.companyKey);
        companyDictDto.setCompanyName(this.companyName);
        companyDictDto.setTaxId(this.taxId);
        return  companyDictDto;
}





public void dataCompare(CompanyInfo resources){

    this.createMan = this.createMan.equals(resources.getCreateMan())?null:resources.getCreateMan();
    this.createTime = this.createTime.equals(resources.getCreateTime()) ?null:resources.getCreateTime()      ;
    this.approveTime =this.approveTime.equals(resources.getApproveTime())?null: resources.getApproveTime();

    if(this.updateMan!=null && resources.getUpdateMan()!=null){
        this.updateMan = this.updateMan.equals(resources.getUpdateMan()!=null)?null:resources.getUpdateMan();
    }
    if(this.updateTime!=null && resources.getUpdateTime()!=null){
        this.updateTime = this.updateTime.equals(resources.getUpdateTime())?null:resources.getUpdateTime();
    }
    if(this.belongArea!=null&&resources.getBelongArea()!=null){
        this.belongArea = this.belongArea.equals(resources.getBelongArea())?null:resources.getBelongArea();
    }
  if(this.belongCompany!=null&&resources.getBelongCompany()!=null){
      this.belongCompany =this.belongCompany.equals(resources.getBelongCompany())?null: resources.getBelongCompany();
  }
   if(this.companyProp!=null&&resources.getCompanyProp()!=null){
       this.companyProp = this.companyProp.equals(resources.getCompanyProp())?null:resources.getCompanyProp();
   }
  if(this.getCompanyShortName()!=null&&resources.getCompanyShortName()!=null){
      this.companyShortName = this.getCompanyShortName().equals(resources.getCompanyShortName())?null:resources.getCompanyShortName();
  }
   if(this.companyState!=null&&resources.getCompanyState()!=null){
       this.companyState = this.companyState.equals(resources.getCompanyState())?null:resources.getCompanyState();
   }
  if(this.customerProp!=null&&resources.getCustomerProp()!=null){
      this.customerProp = this.customerProp.equals(resources.getCustomerProp())?null:resources.getCustomerProp();
  }
  if(this.customerType!=null&&resources.getCustomerType()!=null){
      this.customerType = this.customerType.equals(resources.getCustomerType())?null:resources.getCustomerType();
  }
  if(companyName!=null&&resources.getCompanyName()!=null){
      this.companyName = this.companyName.equals(resources.getCompanyName())?null:resources.getCompanyName();
  }
   if(contactAddress!=null&&resources.getContactAddress()!=null){
       this.contactAddress =this.contactAddress.equals(resources.getContactAddress())?null: resources.getContactAddress();
   }
   if(economicType!=null&resources.getEconomicType()!=null){
       this.economicType = this.economicType.equals(resources.getEconomicType())?null:resources.getEconomicType();
   }
   if(foreignName!=null&&resources.getForeignName()!=null){
       this.foreignName = this.foreignName.equals(resources.getForeignName())?null:resources.getForeignName();
   }
   if(isDisable!=null&&resources.getIsDisable()!=null){
       this.isDisable = this.isDisable.equals(resources.getIsDisable())?null:resources.getIsDisable();
   }
  if(legalbody!=null&&resources.getLegalbody()!=null){
      this.legalbody =this.legalbody.equals(resources.getLegalbody())?null: resources.getLegalbody();
  }
  if(parentCompanyId!=null&&resources.getParentCompanyId()!=null){
      this.parentCompanyId = this.parentCompanyId.equals(resources.getParentCompanyId())?null:resources.getParentCompanyId();
  }
   if(postalCode!=null&&resources.getPostalCode()!=null){
       this.postalCode =this.postalCode.equals(resources.getPostalCode())?null: resources.getPostalCode();
   }
   if(registerfund!=null&&resources.getRegisterfund()!=null){
       this.registerfund =this.registerfund.equals(resources.getRegisterfund())?null: resources.getRegisterfund();
   }
  if(remark!=null&&resources.getRemark()!=null){
      this.remark = this.remark.equals(resources.getRemark())?null:resources.getRemark();
  }
   if(taxId!=null&&resources.getTaxId()!=null){
       this.taxId = this.taxId.equals(resources.getTaxId())?null:resources.getTaxId();
   }

   if(trade!=null&&resources.getTrade()!=null){
       this.trade = this.trade.equals(resources.getTrade())?null:resources.getTrade();
   }
   if(isSynergyPay!=null&&resources.isSynergyPay!=null){
       this.isSynergyPay = this.isSynergyPay.equals(resources.getIsSynergyPay())?null:resources.getIsSynergyPay();
   }
  if(chargeDepartment!=null&&resources.getChargeDepartment()!=null){
      this.chargeDepartment = this.chargeDepartment.equalsIgnoreCase(resources.chargeDepartment)?null: resources.getChargeDepartment();
  }
   if(professionSalesman!=null&&resources.getProfessionSalesman()!=null){
       this.professionSalesman = this.professionSalesman.equals(resources.getProfessionSalesman())?null:resources.getProfessionSalesman();
   }
    if(creditRating!=null&&resources.getCreditRating()!=null){
        this.creditRating =this.creditRating.equals(resources.getCreditRating())?null: resources.getCreditRating();
    }
    if(defaultPaymentAgreement!=null&&resources.defaultPaymentAgreement!=null){
        this.defaultPaymentAgreement =this.defaultPaymentAgreement.equals(resources.getDefaultPaymentAgreement())?null: resources.getDefaultPaymentAgreement();
    }

}


}