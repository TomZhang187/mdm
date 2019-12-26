package com.hqhop.modules.material.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author zf
* @date 2019-12-09
*/
@Data
public class MaterialOperationRecordDTO implements Serializable {

    // 主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long key;

    // 再订购点
    private String againBuyPlace;

    // 审批状态
    private String approvalState;

    // 审批结果
    private String approveResult;

    // 审批时间
    private Timestamp approveTime;

    // 采购员
    private String buyer;

    // 创建人
    private String creator;

    // 创建时间
    private Timestamp createTime;

    // 自定义项
    private String custom;

    // 默认工厂
    private String defaultFactory;

    // 审批链接
    private String dingUrl;

    // 使用状态
    private Boolean enable;

    // 固定提前期
    private String fixedAdvanceTime;

    // 档案主键
    private Long id;

    // 是否批准次核算
    private Boolean isBatchesAccount;

    // 是否按成本中心统计产量
    private Boolean isCenterStatistics;

    // 是否成本对象
    private Boolean isCostObject;

    // 是否需求管理
    private Boolean isDemand;

    // 是否需求合并
    private Boolean isDemandConsolidation;

    // 是否发料
    private Boolean isHairFeed;

    // 是否虚项
    private Boolean isImaginaryTerm;

    // 是否免检
    private Boolean isInspect;

    // 是否根据检验结果入库
    private Boolean isInspectionWarehousing;

    // 是否按生产订单核算成本
    private Boolean isOrderCost;

    // 是否出入库
    private Boolean isOutgoingWarehousing;

    // 是否进行序列号管理
    private Boolean isSerial;

    // 是否应税劳务
    private Boolean isTaxable;

    // 物料型态
    private String materialKenel;

    // 物料等级
    private String materialLevel;

    // 物料类型
    private String materialType;

    // 最高库存
    private String maxStock;

    // 最低库存
    private String minStock;

    // 规格型号
    private String model;

    // 存货名称
    private String name;

    // 操作类型
    private String operationType;

    //  需求管理
    private Boolean outgoingTracking;

    // 委外类型
    private String outsourcingType;

    // 计划属性
    private String planningAttribute;

    // 审批实例ID
    private String processId;

    // 生产部门
    private String productionDepts;

    // 生产业务员
    private String productionSalesman;

    // 标识存货编码
    private String remark;

    // 安全库存
    private String safetyStock;

    // 封存人
    private String sealPerson;

    // 封存标志
    private String sealSign;

    // 封存时间
    private Timestamp sealTime;

    // 税目
    private String taxRating;

    // 单位
    private String unit;

    // 修改人
    private String updatePerson;

    // 修改时间
    private Timestamp updateTime;

    // 钉钉ID
    private String userId;

    // 计价方式
    private String valuationMethod;

    // 货位
    private String zhy;
}