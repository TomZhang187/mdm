package com.hqhop.modules.company.service.dto;

import com.hqhop.annotation.Query;
import lombok.Data;

/**
* @author zf
* @date 2019-11-07
*/
@Data
public class ContactQueryCriteria{

    // 精确
    @Query
    private Long contactKey;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String contactAddress;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String email;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String phone;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String position;

    // 精确
    @Query
    private Long companyKey;

    // 精确
    @Query
    private Integer contactType;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryAddress;

    // 精确
    @Query
    private Integer isDefaultAddress;
}
