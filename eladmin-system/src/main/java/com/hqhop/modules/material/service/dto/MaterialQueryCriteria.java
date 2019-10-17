package com.hqhop.modules.material.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author chengy
* @date 2019-10-17
*/
@Data
public class MaterialQueryCriteria{

    // 精确
    @Query
    private String materialModel;

    // 精确
    @Query
    private String materialName;

    // 精确
    @Query
    private String materialNumber;
}