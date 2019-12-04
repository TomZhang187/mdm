package com.hqhop.modules.company.service.dto;

import com.hqhop.annotation.Query;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author zf
* @date 2019-11-07
*/
@Data
public class CompanyUpdateDTO implements Serializable {

    // 操作记录主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long operateKey;

    // 审批结果
    private Integer approveResult;

    // 审批时间
    private Timestamp approveTime;

    // 所属地区
    private Integer belongArea;

    // 所属公司
    private Integer belongCompany;

    // 公司主键
    private Long companyKey;

    // 公司属性
    private Integer companyProp;

    // 公司简称
    private String companyShortName;

    // 公司状态
    private Integer companyState;

    // 公司类型
    private Integer companyType;

    // 公司名称
    private String compayName;

    // 联系地址
    private String contactAddress;

    // 创建人
    private String createMan;

    // 经济类型
    private Integer economicType;

    // 外文名称
    private String foreignName;

    // 是否停用
    private Integer isDisable;

    // 是否散户
    private Integer isRetai;

    // 是否协同付款
    private Integer isSynergyPay;

    // 法人
    private String legalbody;

    // 操作时间
    private Timestamp operateTime;

    // 总公司
    private Long parentCompanyId;

    // 邮政编码
    private String postalCode;

    // 审批ID
    private String processId;

    // 注册资金
    private BigDecimal registerfund;

    // 备注
    private String remark;

    // 纳税编号
    private String taxId;

    // 所属行业
    private Integer trade;

    // 操作人ID
    private Long userId;

    // 操作类型
    private Integer  operationType;
}