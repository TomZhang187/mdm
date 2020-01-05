package com.hqhop.modules.company.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

import java.util.Set;

/**
* @author zf
* @date 2019-11-06
*/
@Data
public class AccountQueryCriteria{

    // 精确
    @Query
    private Long accountKey;


    @Query(type = Query.Type.IN, propName="accountKey")
    private Set<Long> keys;

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
