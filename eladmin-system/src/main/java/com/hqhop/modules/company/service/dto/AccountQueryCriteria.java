package com.hqhop.modules.company.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

/**
* @author zf
* @date 2019-11-06
*/
@Data
public class AccountQueryCriteria{

    // 精确
    @Query
    private Long accountKey;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String account;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String accountBlank;

    // 精确
    @Query
    private Integer blankClass;

    // 精确
    @Query
    private Integer currency;

    // 精确
    @Query
    private Integer isDefalut;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
}
