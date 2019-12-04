package com.hqhop.modules.material.service.dto;


import com.hqhop.annotation.Query;
import lombok.Data;

import java.util.Set;

/**
 * @author KinLin
 * @date 2019-10-30
 */
@Data
public class MaterialTypeQueryCriteria {

    @Query(type = Query.Type.IN, propName="typeId")
    private Set<Long> ids;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;
    @Query
    private Long pid;
    @Query
    private Boolean enabled;
}
