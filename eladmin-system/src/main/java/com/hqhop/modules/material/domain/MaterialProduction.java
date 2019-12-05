package com.hqhop.modules.material.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hqhop.modules.company.domain.Account;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wst
 * @date 2019-11-26
 */
@Entity
@Data
@Table(name = "material_production")

public class MaterialProduction implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // 需求管理
    @Column(name = "outgoing_tracking")
    private Boolean outgoingTracking;

    // 是否需求管理
    @Column(name = "is_demand")
    private Boolean isDemand;

    // 是否进行序列号管理
    @Column(name = "is_serial")
    private Boolean isSerial;

    // 默认工厂
    @Column(name = "default_factory")
    private String defaultFactory;

    // 自定义项
    @Column(name = "custom")
    private String custom;

    // 物料类型
    @Column(name = "material_type")
    private String materialType;

    // 物料型态
    @Column(name = "material_kenel")
    private String materialKenel;

    // 是否需求合并
    @Column(name = "is_demand_consolidation")
    private Boolean isDemandConsolidation;

    // 是否虚项
    @Column(name = "is_imaginary_term")
    private Boolean isImaginaryTerm;

    // 是否成本对象
    @Column(name = "is_cost_object")
    private Boolean isCostObject;

    // 是否发料
    @Column(name = "is_hair_feed")
    private Boolean isHairFeed;

    // 是否根据检验结果入库
    @Column(name = "is_inspection_warehousing")
    private Boolean isInspectionWarehousing;

    // 是否免检
    @Column(name = "is_inspect")
    private Boolean isInspect;

    // 是否按生产订单核算成本
    @Column(name = "is_order_cost")
    private Boolean isOrderCost;

    // 是否按成本中心统计产量
    @Column(name = "is_center_statistics")
    private Boolean isCenterStatistics;

    // 是否出入库
    @Column(name = "is_outgoing_warehousing")
    private Boolean isOutgoingWarehousing;

    // 计价方式
    @Column(name = "valuation_method")
    private String valuationMethod;
    //委外类型
    @Column(name = "outsourcing_type")
    private String outsourcingType;
    // 生产业务员
    @Column(name = "production_salesman")
    private String productionSalesman;
    @Column(name = "material_level")
    private String materialLevel;

    // 计划属性
    @Column(name = "planning_attribute")
    private String planningAttribute;
    // 对应物料
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    private Material material;

   /* public void copy(MaterialProduction source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }*/

    public void copy( MaterialProduction source){
        BeanUtil.copyProperties( source,this, CopyOptions.create().setIgnoreNullValue(true));
    }



}