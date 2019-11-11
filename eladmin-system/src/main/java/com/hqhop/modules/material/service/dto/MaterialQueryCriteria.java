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

    @Query
    private String name;

    @Query
    private String remark;
    @Query
    private String classifyNum;
    @Query
    private String bigType;
    @Query
    private String isTaxable;
    @Query
    private String unit;


    @Query
    private Timestamp createTime;


}