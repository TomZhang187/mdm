package com.hqhop.modules.company.service.dto;

import com.hqhop.modules.company.domain.Contact;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


/**
* @author zf
* @date 2019-10-22
*/
@Data
public class CompanyInfoDTO implements Serializable {

    // 客商ID
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long companyKey;

    // 批准时间
    @Column(name = "approve_time")
    private Timestamp approveTime;

    // 所属地区
    private Integer belongArea;

    // 所属公司
    private Integer belongCompany;

    // 公司属性
    private Integer companyProp;

    // 客商简称
    private String companyShortName;

    // 客商状态
    private Integer companyState;

    // 客商类型
    private Integer companyType;

    // 客商名称
    private String compayName;

    // 通讯地址
    private String contactAddress;

    // 经济类型
    private Integer economicType;

    // 外文名称
    private String foreignName;

    // 是否禁用
    private Integer isDisable;

    // 是否散户
    private Integer isRetai;

    // 法人
    private String legalbody;

    // 总公司编码
    private Long parentCompanyId;

    // 邮政编码
    private String postalCode;

    // 注册资金
    private BigDecimal registerfund;

    // 备注
    private String remark;

    // 税务登记号
    private String taxId;

    // 所属行业
    private Integer trade;

    //联系人
    private Set<Contact > contacts = new HashSet<>();

    public void setApproveTime(Timestamp approveTime) {


        this.approveTime =null;
    }

}