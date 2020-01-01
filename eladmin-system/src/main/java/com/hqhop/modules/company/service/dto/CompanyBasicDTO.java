package com.hqhop.modules.company.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author lyl
* @date 2020-01-01
*/
@Data
public class CompanyBasicDTO implements Serializable {

    private String taxId;

    private String companyName;

    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long key;

    private String companyShortName;

    private String headOfficeCode;

    private String customerType;

    private String belongCompany;

    private String belongArea;

    private String createMan;

    private Timestamp createTime;

    private String updateMan;

    private Timestamp updateTime;
}