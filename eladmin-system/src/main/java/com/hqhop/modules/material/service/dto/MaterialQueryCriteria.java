package com.hqhop.modules.material.service.dto;

import com.hqhop.annotation.Query;
import com.hqhop.modules.material.domain.MaterialType;
import lombok.Data;

import java.sql.Timestamp;

/**
* @author KinLin
* @date 2019-10-30
*/
@Data
public class MaterialQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String name;
    @Query
    private String remark;
    @Query
    private String isTaxable;
    @Query
    private String unit;
    @Query (propName = "id", joinName = "type")
    private String typeId;
    @Query
    private Timestamp createTime;


}