package com.hqhop.modules.company.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyDictDto implements Serializable {


    // 客商ID
    private Long companyKey;


    // 客商名称
    private String companyName;


    // 税务登记号
    private String taxId;



}
