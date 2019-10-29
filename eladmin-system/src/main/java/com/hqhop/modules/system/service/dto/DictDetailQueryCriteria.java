package com.hqhop.modules.system.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;

    // 精确
    @Query
    private String value;
}
