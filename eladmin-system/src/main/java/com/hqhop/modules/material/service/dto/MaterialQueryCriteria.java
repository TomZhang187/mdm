package com.hqhop.modules.material.service.dto;

import com.hqhop.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;

/**
* @author KinLin
* @date 2019-10-30
*/
@Data
public class MaterialQueryCriteria{

    // 精确
    @Query
    private Timestamp createTime;


}