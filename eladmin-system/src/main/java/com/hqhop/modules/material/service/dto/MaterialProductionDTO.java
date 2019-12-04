package com.hqhop.modules.material.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hqhop.modules.material.domain.Material;
import lombok.Data;

import java.io.Serializable;


/**
 * @author wst
 * @date 2019-11-26
 */
@Data

public class MaterialProductionDTO implements Serializable {

    // 出库跟踪入库
    private Integer id;

    // 需求管理
    private Boolean outgoingTracking;

    // 是否需求管理
    private Boolean isDemand;

    // 是否进行序列号管理
    private Boolean isSerial;

    // 默认工厂
    private String defaultFactory;

    // 自定义项
    private String custom;

    // 物料类型
    private String materialType;

    // 物料型态
    private String materialKenel;

    // 是否需求合并
    private Boolean isDemandConsolidation;

    // 是否虚项
    private Boolean isImaginaryTerm;

    // 是否成本对象
    private Boolean isCostObject;

    // 是否发料
    private Boolean isHairFeed;

    // 是否根据检验结果入库
    private Boolean isInspectionWarehousing;

    // 是否免检
    private Boolean isInspect;

    // 是否按生产订单核算成本
    private Boolean isOrderCost;

    // 是否按成本中心统计产量
    private Boolean isCenterStatistics;

    // 是否出入库
    private Boolean isOutgoingWarehousing;

    // 计价方式
    private String valuationMethod;

    // 生产业务员
    private String productionSalesman;

    // 计划属性
    private String planningAttribute;

    //委外类型
    private String outsourcingType;

    //关联物料
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private Material material;
    
    //物料级别
    private String materialLevel;

    public MaterialProductionDTO() {
    }

}