package com.hqhop.modules.material.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
* @author zf
* @date 2019-12-09
*/
@Entity
@Data
@Table(name="material_operation_record")
public class MaterialOperationRecord implements Serializable {

    // 主键
    @Id
    @Column(name = "key")
    private Long key;

    // 再订购点
    @Column(name = "again_buy_place")
    private String againBuyPlace;

    // 审批状态
    @Column(name = "approval_state")
    private String approvalState;

    // 审批结果
    @Column(name = "approve_result")
    private String approveResult;

    // 审批时间
    @Column(name = "approve_time")
    private Timestamp approveTime;

    // 采购员
    @Column(name = "buyer")
    private String buyer;

    // 创建人
    @Column(name = "creator")
    private String creator;

    // 创建时间
    @Column(name = "create_time")
    private Timestamp createTime;

    // 自定义项
    @Column(name = "custom")
    private String custom;

    // 默认工厂
    @Column(name = "default_factory")
    private String defaultFactory;

    // 审批链接
    @Column(name = "ding_url")
    private String dingUrl;

    // 使用状态
    @Column(name = "enable")
    private Boolean enable;

    // 固定提前期
    @Column(name = "fixed_advance_time")
    private String fixedAdvanceTime;

    // 档案主键
    @Column(name = "id")
    private Long id;

    // 是否批准次核算
    @Column(name = "is_batches_account")
    private Boolean isBatchesAccount;

    // 是否按成本中心统计产量
    @Column(name = "is_center_statistics")
    private Boolean isCenterStatistics;

    // 是否成本对象
    @Column(name = "is_cost_object")
    private Boolean isCostObject;

    // 是否需求管理
    @Column(name = "is_demand")
    private Boolean isDemand;

    // 是否需求合并
    @Column(name = "is_demand_consolidation")
    private Boolean isDemandConsolidation;

    // 是否发料
    @Column(name = "is_hair_feed")
    private Boolean isHairFeed;

    // 是否虚项
    @Column(name = "is_imaginary_term")
    private Boolean isImaginaryTerm;

    // 是否免检
    @Column(name = "is_inspect")
    private Boolean isInspect;

    // 是否根据检验结果入库
    @Column(name = "is_inspection_warehousing")
    private Boolean isInspectionWarehousing;

    // 是否按生产订单核算成本
    @Column(name = "is_order_cost")
    private Boolean isOrderCost;

    // 是否出入库
    @Column(name = "is_outgoing_warehousing")
    private Boolean isOutgoingWarehousing;

    // 是否进行序列号管理
    @Column(name = "is_serial")
    private Boolean isSerial;

    // 是否应税劳务
    @Column(name = "is_taxable")
    private Boolean isTaxable;

    // 物料型态
    @Column(name = "material_kenel")
    private String materialKenel;

    // 物料等级
    @Column(name = "material_level")
    private String materialLevel;

    // 物料类型
    @Column(name = "material_type")
    private String materialType;

    // 最高库存
    @Column(name = "max_stock")
    private String maxStock;

    // 最低库存
    @Column(name = "min_stock")
    private String minStock;

    // 规格型号
    @Column(name = "model")
    private String model;

    // 存货名称
    @Column(name = "name")
    private String name;

    // 操作类型
    @Column(name = "operation_type")
    private String operationType;

    //  需求管理
    @Column(name = "outgoing_tracking")
    private Boolean outgoingTracking;

    // 委外类型
    @Column(name = "outsourcing_type")
    private String outsourcingType;

    // 计划属性
    @Column(name = "planning_attribute")
    private String planningAttribute;

    // 审批实例ID
    @Column(name = "process_id")
    private String processId;

    // 生产部门
    @Column(name = "production_depts")
    private String productionDepts;

    // 生产业务员
    @Column(name = "production_salesman")
    private String productionSalesman;

    // 标识存货编码
    @Column(name = "remark")
    private String remark;

    // 安全库存
    @Column(name = "safety_stock")
    private String safetyStock;

    // 封存人
    @Column(name = "seal_person")
    private String sealPerson;

    // 封存标志
    @Column(name = "seal_sign")
    private String sealSign;

    // 封存时间
    @Column(name = "seal_time")
    private Timestamp sealTime;

    // 税目
    @Column(name = "tax_rating")
    private String taxRating;

    // 单位
    @Column(name = "unit")
    private String unit;

    // 修改人
    @Column(name = "update_person")
    private String updatePerson;

    // 修改时间
    @Column(name = "update_time")
    private Timestamp updateTime;

    // 钉钉ID
    @Column(name = "user_id")
    private String userId;

    // 计价方式
    @Column(name = "valuation_method")
    private String valuationMethod;

    // 货位
    @Column(name = "zhy")
    private String zhy;


    //附件集合
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Accessory.class, cascade = CascadeType.PERSIST )
    @JoinColumn(name = "key")
    private Set<Accessory> accessories = new HashSet<>();




    public void copy(MaterialOperationRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public  Material getMaterial(){
        Material material = new Material();
        material.setId(this.id);
        material.setApprovalState(this.approvalState);
        material.setUnit(this.unit);
        material.setUpdateTime(this.updateTime);
        material.setUpdatePerson(this.updatePerson);
        material.setCreateTime(this.createTime);
        material.setCreatePerson(this.creator);
        material.setTaxRating(this.taxRating);
        material.setName(this.name);
        material.setModel(this.model);
        material.setIsTaxable(this.isTaxable);
        material.setRemark(this.remark);
        return material;
    }














}