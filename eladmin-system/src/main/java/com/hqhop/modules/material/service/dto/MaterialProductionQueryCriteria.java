package com.hqhop.modules.material.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

/**
* @author wst
* @date 2019-11-26
*/
@Data
public class MaterialProductionQueryCriteria{

    // 精确
    @Query
    private Integer id;

    // 精确
    @Query
    private Boolean outgoingTracking;

    // 精确
    @Query
    private Boolean isDemand;

    // 精确
    @Query
    private Boolean isSerial;


    // 精确
    @Query
    private Boolean enable;


    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String defaultFactory;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String custom;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String materialType;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String materialKenel;

    // 精确
    @Query
    private Boolean isDemandConsolidation;

    // 精确
    @Query
    private Boolean isImaginaryTerm;
    //模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String outsourcingType;
    // 精确
    @Query
    private Boolean iscostObject;

    // 精确
    @Query
    private Boolean isHairFeed;

    // 精确
    @Query
    private Boolean isInspectionWarehousing;

    // 精确
    @Query
    private Boolean isInspect;

    // 精确
    @Query
    private Boolean isOrderCost;

    // 精确
    @Query
    private Boolean isCenterStatistics;

    // 精确
    @Query
    private Boolean isOutgoingWarehousing;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String valuationMethod;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String productionSalesman;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String 

planningAttribute;
}
