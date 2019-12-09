package com.hqhop.modules.material.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hqhop.config.dingtalk.domain.Accessory;
import com.hqhop.modules.company.domain.Contact;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/9 0009 9:31
 * @description：物料操作记录对象
 * @modified By：
 * @version: $
 */
@Data
public class MaterialOperationRecord {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "material_id")
        private Long materialId;

        //标识存货编码
        @Column(name = "remark")
        private String remark;

        //存货名称
        @Column(name = "name")
        private String name;

        //是否应税劳务
        @Column(name = "is_taxable")
        private Boolean isTaxable;

        //规格型号
        @Column(name = "model")
        private String model;

        //税目
        @Column(name = "tax_rating")
        private String taxRating;

        //创建者
        @Column(name = "creator")
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

        //单位
        @Column(name = "unit")
        private String unit;

        //审批状态
        @Column(name = "approval_state")
        private String approvalState;



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

        //是否批准次核算
        @Column(name = "is_batches_account")
        private Boolean isBatchesAccount;

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


        // 审批实例ID
        @Column(name = "process_id")
        private String processId;


        // 审批结果
        @Column(name = "approve_result")
        private String approveResult="未知";

        // 操作人ID
        @Column(name = "user_id")
        private String userId;

    // 审批时间
    @Column(name = "approve_time")
    private Timestamp approveTime;

    // 操作类型
    @Column(name = "operation_type")
    private String  operationType;

    // 审批链接
    @Column(name = "ding_url")
    private String dingUrl;


    //附加集合
    //联系人
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Contact.class, cascade = CascadeType.PERSIST )
    @JoinColumn(name = "material_id")
    private Set<Accessory> Accessorys = new HashSet<>();



        public void copy(MaterialOperationRecord source) {
            BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
        }


    }















