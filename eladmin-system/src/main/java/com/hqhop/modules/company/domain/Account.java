package com.hqhop.modules.company.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import javax.persistence.*;
import java.io.Serializable;

/**
* @author zf
* @date 2019-11-06
*/
@Entity
@Table(name="account")
public class Account implements Serializable {


    //  账户主键
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_key")
    private Long accountKey;

    // 账号
    @Column(name = "account")
    private String account;

    // 开户行
    @Column(name = "account_blank")
    private String accountBlank;

    // 银行类型
    @Column(name = "blank_class",nullable = false)
    private String blankClass;

    // 币种
    @Column(name = "currency")
    private String currency;

    // 是否默认
    @Column(name = "is_defalut")
    private Integer isDefalut;

    // 账户名
    @Column(name = "account_name")
    private String accountName;

    //账户状态
    @Column(name = "account_state")
    private Integer accountState;
    //绑定公司
    @Column(name = "company_key")
    private Long companyKey;

    //所属公司
    @Column(name = "belong_company")
    private String belongCompany;

    public void copy(Account source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountKey=" + accountKey +
                ", account='" + account + '\'' +
                ", accountBlank='" + accountBlank + '\'' +
                ", blankClass=" + blankClass +
                ", currency=" + currency +
                ", isDefalut=" + isDefalut +
                ", accountName='" + accountName + '\'' +
                ", accountState=" + accountState +
                ", companyKey=" + companyKey +
                ", belongCompany=" + belongCompany +
                '}';
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBelongCompany() {
        return belongCompany;
    }

    public void setBelongCompany(String belongCompany) {
        this.belongCompany = belongCompany;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public Long getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(Long accountKey) {
        this.accountKey = accountKey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountBlank() {
        return accountBlank;
    }

    public void setAccountBlank(String accountBlank) {
        this.accountBlank = accountBlank;
    }

    public String getBlankClass() {
        return blankClass;
    }

    public void setBlankClass(String blankClass) {
        this.blankClass = blankClass;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getIsDefalut() {
        return isDefalut;
    }

    public void setIsDefalut(Integer isDefalut) {
        this.isDefalut = isDefalut;
    }


    public Long getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Long companyKey) {
        this.companyKey = companyKey;
    }
}