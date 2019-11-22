package com.hqhop.modules.company.domain;

import lombok.Data;
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


    // 账户主键
    @Id
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
    private Integer blankClass;

    // 币种
    @Column(name = "currency")
    private Integer currency;

    // 是否默认
    @Column(name = "is_defalut",nullable = false)
    private Integer isDefalut;

    // 账户名
    @Column(name = "name")
    private String name;

    @Column(name = "company_key")
    private Long companyKey;

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
                ", name='" + name + '\'' +
                ", companyKey=" + companyKey +
                '}';
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

    public Integer getBlankClass() {
        return blankClass;
    }

    public void setBlankClass(Integer blankClass) {
        this.blankClass = blankClass;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getIsDefalut() {
        return isDefalut;
    }

    public void setIsDefalut(Integer isDefalut) {
        this.isDefalut = isDefalut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Long companyKey) {
        this.companyKey = companyKey;
    }
}