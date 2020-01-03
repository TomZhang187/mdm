package com.hqhop.modules.company.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

import java.math.BigDecimal;

/**
* @author zf
* @date 2019-11-07
*/
@Data
public class CompanyUpdateQueryCriteria{

    // 精确
    @Query
    private Long operateKey;

    // 精确
    @Query
    private Integer approveResult;

    // 精确
    @Query
    private Integer belongArea;

    // 精确
    @Query
    private Integer belongCompany;

    // 精确
    @Query
    private Long companyKey;

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

    // 精准
    @Query
    private String createMan;

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

    // 精确
    @Query
    private Integer isSynergyPay;

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
    private String processId;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private BigDecimal registerfund;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String taxId;

    // 精确
    @Query
    private Integer trade;

    // 精确
    @Query
    private Long userId;
}
