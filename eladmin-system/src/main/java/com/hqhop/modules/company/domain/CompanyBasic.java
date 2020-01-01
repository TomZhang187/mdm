package com.hqhop.modules.company.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author lyl
* @date 2020-01-01
*/
@Entity
@Data
@Table(name="company_basic")
public class CompanyBasic implements Serializable {

    @Id
    @Column(name = "key")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long key;


    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "company_name")
    private String companyName;



    @Column(name = "company_short_name")
    private String companyShortName;

    @Column(name = "head_office_code")
    private String headOfficeCode;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "belong_company")
    private String belongCompany;

    @Column(name = "belong_area")
    private String belongArea;

    @Column(name = "create_man")
    private String createMan;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_man")
    private String updateMan;

    @Column(name = "update_time")
    private Timestamp updateTime;

    public void copy(CompanyBasic source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}