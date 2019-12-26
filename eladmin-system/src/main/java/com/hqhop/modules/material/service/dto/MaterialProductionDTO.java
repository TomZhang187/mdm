package com.hqhop.modules.material.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hqhop.modules.material.domain.Material;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


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


    //原物料编码
    private String originalRemark;

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


    //创建者
    private String createPerson;

    //创建时间
    private Timestamp createTime;

    //修改者
    private String updatePerson;

    //修改日期
    private Timestamp updateTime;


    //采购员
    private String buyer;

    //货位
    private String zhy;

    //是否封存
    private  Boolean enable;

    //封存时间
    private Timestamp sealTime;

    //封存标志
    private String sealSign;

    //封存人
    private String sealPerson;

    //是否批准次核算
    private Boolean isBatchesAccount;

    //安全库存
    private String safetyStock;

    //最低库存
    private String minStock;

    //最高库存
    private String maxStock;

    //再订购点
    private String againBuyPlace;

    //生产部门
    private String productionDepts;

    //固定提前期
    private  String  fixedAdvanceTime;

    //审批状态
    private String approvalState;

    public MaterialProductionDTO() {
    }

}