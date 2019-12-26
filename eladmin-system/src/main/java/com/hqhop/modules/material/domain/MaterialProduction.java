package com.hqhop.modules.material.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hqhop.modules.company.domain.Account;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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


    //原物料编码
    @Column(name = "original_remark")
    private String originalRemark;

    //出库跟踪入库
    @Column(name = "is_out_track_warehousing")
    private Boolean isOutTrackWarehousing;

    // 需求管理
    @Column(name = "outgoing_tracking")
    private Boolean outgoingTracking;

    // 是否需求管理
    @Column(name = "is_demand")
    private Boolean isDemand;

    // 是否进行序列号管理
    @Column(name = "is_serial")
    private Boolean isSerial;

    //是否批次管理
    @Column(name = "is_batch_management")
    private Boolean IsBatchManagement;

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


    //是否批次核算
    @Column(name = "is_batches_account")
    private Boolean isBatchesAccount;

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


    //创建者
    @Column(name = "create_person")
    private String createPerson;

    //创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    //修改者
    @Column(name = "update_person")
    private String updatePerson;

    //修改日期
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;


    //采购员
    @Column(name = "buyer")
    private String buyer;

    //货位
    @Column(name = "zhy")
    private String zhy;

    //是否封存
    @Column(name = "enable")
    private  Boolean enable;

    //封存时间
    @Column(name = "seal_time")
    private Timestamp sealTime;

    //封存标志
    @Column(name = "seal_sign")
    private String sealSign;

    //封存人
    @Column(name = "seal_person")
    private String sealPerson;



    //安全库存
    @Column(name = "safety_stock")
    private String safetyStock;

    //最低库存
    @Column(name = "min_stock")
    private String minStock;

    //最高库存
    @Column(name = "max_stock")
    private String maxStock;

    //再订购点
    @Column(name = "again_buy_place")
    private String againBuyPlace;

    //生产部门
    @Column(name = "production_depts")
    private String productionDepts;

    //固定提前期
    @Column(name = "fixed_advance_time")
     private  String  fixedAdvanceTime;

    //审批状态
    @Column(name = "approval_state")
    private String approvalState;


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