package com.hqhop.modules.company.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hqhop.annotation.Query;
import lombok.Data;


import java.sql.Timestamp;
import java.util.Date;

/**
 * @author zf
 * @date 2019-10-22
 */
@Data
public class CompanyInfoQueryCriteria {

    // 客商ID
    // 处理精度丢失问题
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companyKey;

    // 批准时间
    private Timestamp approveTime;

    // 精确
    @Query
    private Integer belongArea;

    // 精确
    @Query
    private Integer belongCompany;

    // 精确
    @Query
    private Integer companyProp;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String companyShortName;

    // 精确
    @Query
    private Integer companyState;

    // 精确
    @Query
    private Integer companyType;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String compayName;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String contactAddress;

    // 精确
    @Query
    private Integer economicType;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String foreignName;

    // 精确
    @Query
    private Integer isDisable;

    // 精确
    @Query
    private Integer isRetai;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String legalbody;

    // 精确
    @Query
    private Long parentCompanyId;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String postalCode;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String taxId;

    // 精确
    @Query
    private Integer trade;

    //联系人的名字 模糊查询
    private String contactName;

}