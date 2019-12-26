package com.hqhop.modules.material.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

/**
* @author zf
* @date 2019-12-09
*/
@Data
public class MaterialOperationRecordQueryCriteria{

    // 精确
    @Query
    private Long key;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String againBuyPlace;

    // 精确
    @Query
    private String approvalState;

    // 精确
    @Query
    private String approveResult;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String buyer;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String creator;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String custom;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String defaultFactory;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String dingUrl;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private Boolean enable;

    // 精确
    @Query
    private Long id;

    // 精确
    @Query
    private Boolean isBatchesAccount;

    // 精确
    @Query
    private Boolean isCenterStatistics;

    // 精确
    @Query
    private Boolean isCostObject;

    // 精确
    @Query
    private Boolean isDemand;

    // 精确
    @Query
    private Boolean isDemandConsolidation;

    // 精确
    @Query
    private Boolean isHairFeed;

    // 精确
    @Query
    private Boolean isImaginaryTerm;

    // 精确
    @Query
    private Boolean isInspect;

    // 精确
    @Query
    private Boolean isInspectionWarehousing;

    // 精确
    @Query
    private Boolean isOrderCost;

    // 精确
    @Query
    private Boolean isOutgoingWarehousing;

    // 精确
    @Query
    private Boolean isSerial;

    // 精确
    @Query
    private Boolean isTaxable;

    // 精确
    @Query
    private String materialKenel;

    // 精确
    @Query
    private String materialLevel;

    // 精确
    @Query
    private String materialType;

    // 精确
    @Query
    private String model;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    // 精确
    @Query
    private String operationType;

    // 精确
    @Query
    private Boolean outgoingTracking;

    // 精确
    @Query
    private String outsourcingType;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String planningAttribute;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String processId;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String productionDepts;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String productionSalesman;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;

    // 精确
    @Query
    private String safetyStock;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String sealPerson;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String sealSign;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String taxRating;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String updatePerson;

    // 精确
    @Query
    private String userId;

    // 精确
    @Query
    private String valuationMethod;

    // 精确
    @Query
    private String zhy;
}
