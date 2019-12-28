package com.hqhop.modules.material.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

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


    /*物料记录独有属性
    * */
    @Id
    @Column(name = "key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    //钉钉审批链接
    @Column(name = "ding_url")
    private String dingUrl;

    //审批时间
    @Column(name = "approve_time")
    private Timestamp approveTime;


    //审批结果
    @Column(name = "approve_result")
    private String approveResult;


   //物料基本档案临时保存数据 ID
    @Column(name = "temporary_id")
    private Long temporaryId;

    //记录创建人
    @Column(name = "creator")
    private String creator;


    //操作类型
    @Column(name = "operation_type")
    private String operationType;

    //审批实例ID
    @Column(name = "process_id")
    private String processId;





    /*物料基本档案和生产档案的属性
     * */

    //出库跟踪入库
    @Column(name = "is_out_track_warehousing")
    private Boolean isOutTrackWarehousing;

    @Column(name = "again_buy_place")
    private String againBuyPlace;

    @Column(name = "approval_state")
    private String approvalState;


    @Column(name = "buyer")
    private String buyer;

    @Column(name = "create_person")
    private String createPerson;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "custom")
    private String custom;

    @Column(name = "default_factory")
    private String defaultFactory;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "fixed_advance_time")
    private String fixedAdvanceTime;

    @Column(name = "id")
    private Long id;

    @Column(name = "is_batches_account")
    private Boolean isBatchesAccount;

    @Column(name = "is_center_statistics")
    private Boolean isCenterStatistics;

    @Column(name = "is_cost_object")
    private Boolean isCostObject;

    @Column(name = "is_demand")
    private Boolean isDemand;

    @Column(name = "is_demand_consolidation")
    private Boolean isDemandConsolidation;

    @Column(name = "is_hair_feed")
    private Boolean isHairFeed;

    @Column(name = "is_imaginary_term")
    private Boolean isImaginaryTerm;

    @Column(name = "is_inspect")
    private Boolean isInspect;

    @Column(name = "is_inspection_warehousing")
    private Boolean isInspectionWarehousing;

    @Column(name = "is_order_cost")
    private Boolean isOrderCost;

    @Column(name = "is_outgoing_warehousing")
    private Boolean isOutgoingWarehousing;

    @Column(name = "is_serial")
    private Boolean isSerial;

    @Column(name = "is_taxable")
    private Boolean isTaxable;

    @Column(name = "material_kenel")
    private String materialKenel;

    @Column(name = "material_level")
    private String materialLevel;

    @Column(name = "material_type")
    private String materialType;

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "max_stock")
    private String maxStock;

    @Column(name = "min_stock")
    private String minStock;

    @Column(name = "model")
    private String model;

    @Column(name = "name")
    private String name;


    @Column(name = "outgoing_tracking")
    private Boolean outgoingTracking;

    @Column(name = "outsourcing_type")
    private String outsourcingType;

    @Column(name = "planning_attribute")
    private String planningAttribute;


    @Column(name = "production_depts")
    private String productionDepts;

    @Column(name = "production_salesman")
    private String productionSalesman;

    @Column(name = "remark")
    private String remark;

    @Column(name = "safety_stock")
    private String safetyStock;

    @Column(name = "seal_person")
    private String sealPerson;

    @Column(name = "seal_sign")
    private String sealSign;

    @Column(name = "seal_time")
    private Timestamp sealTime;

    @Column(name = "tax_rating")
    private String taxRating;


    @Column(name = "unit")
    private String unit;

    @Column(name = "update_person")
    private String updatePerson;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "valuation_method")
    private String valuationMethod;

    @Column(name = "zhy")
    private String zhy;





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
        material.setCreatePerson(this.createPerson);
        material.setTaxRating(this.taxRating);
        material.setName(this.name);
        material.setModel(this.model);
        material.setIsTaxable(this.isTaxable);
        material.setRemark(this.remark);
        return material;
    }

    public  void getDataByMaterial(Material material) {
//        this.id = material.getId();
//        this.approvalState = material.getApprovalState();
        this.unit = material.getUnit();
//        this.updatePerson = material.getUpdatePerson();
//        this.updateTime = material.getUpdateTime();
//        this.createTime = material.getCreateTime();
//        this.creator = material.getCreatePerson();
        this.taxRating = material.getTaxRating();
        this.name = material.getName();
        this.model = material.getModel();
        this.isTaxable = material.getIsTaxable();
        this.remark = material.getRemark();
    }



    public void getDataByMateriaProduction(MaterialProduction resources){

        if(resources.getId() != null){
            this.id = Long.valueOf(resources.getId());
        }
        this.isDemandConsolidation = resources.getIsDemandConsolidation();
        this.custom = resources.getCustom();
        this.defaultFactory = resources.getDefaultFactory();
        this.isCenterStatistics = resources.getIsCenterStatistics();
        this.isCostObject = resources.getIsCostObject();
        this.isDemand = resources.getIsDemand();
        this.isHairFeed = resources.getIsHairFeed();
        this.isImaginaryTerm = resources.getIsImaginaryTerm();
        this.isInspect = resources.getIsInspect();
        this.isInspectionWarehousing = resources.getIsInspectionWarehousing();
        this.isOrderCost = resources.getIsOrderCost();
        this.isOutgoingWarehousing = resources.getIsOutgoingWarehousing();
        this.isSerial = resources.getIsSerial();
        this.materialKenel = resources.getMaterialKenel();
        this.materialType = resources.getMaterialType();
        this.outgoingTracking = resources.getOutgoingTracking();
        this.outsourcingType = resources.getOutsourcingType();
        this.planningAttribute = resources.getPlanningAttribute();
        this.productionSalesman = resources.getProductionSalesman();
        this.valuationMethod = resources.getValuationMethod();
        this.materialId = resources.getMaterial().getId();
        this.materialLevel = resources.getMaterialLevel();
        this.againBuyPlace = resources.getAgainBuyPlace();
        this.buyer = resources.getBuyer();
        this.createPerson = resources.getCreatePerson();
        this.createTime = resources.getCreateTime();
        this.enable = resources.getEnable();
        this.fixedAdvanceTime = resources.getFixedAdvanceTime();
        this.isBatchesAccount = resources.getIsBatchesAccount();
        this.maxStock = resources.getMaxStock();
        this.minStock = resources.getMinStock();
        this.productionDepts = resources.getProductionDepts();
        this.safetyStock = resources.getSafetyStock();
        this.sealPerson = resources.getSealPerson();
        this.sealSign = resources.getSealSign();
        this.sealTime = resources.getSealTime();
        this.updatePerson = resources.getUpdatePerson();
        this.updateTime = resources.getUpdateTime();
        this.zhy = resources.getZhy();
       this.approvalState = resources.getApprovalState();
       this.materialType = resources.getMaterialType();
       this.enable = resources.getEnable();
       this.name = resources.getMaterial().getName();
       this.model = resources.getMaterial().getModel();
    }

    public MaterialProduction getMaterialProduction(){
        MaterialProduction object = new MaterialProduction();
        if(this.getId() != null){
            object.setId(this.getId().intValue());
        }
        object.setIsDemandConsolidation(this.isDemandConsolidation);
        object.setCustom(this.getCustom());
        object.setDefaultFactory(this.getDefaultFactory());
        object.setIsCenterStatistics(this.getIsCenterStatistics());
        object.setIsCostObject(this.getIsCostObject());
        object.setIsDemand(this.getIsDemand());
        object.setIsHairFeed(this.getIsHairFeed());
        object.setIsImaginaryTerm(this.getIsImaginaryTerm());
        object.setIsInspect(this.getIsInspect());
        object.setIsInspectionWarehousing(this.getIsInspectionWarehousing());
        object.setIsOrderCost(this.getIsOrderCost());
        object.setIsOutgoingWarehousing(this.getIsOutgoingWarehousing());
        object.setIsSerial(this.getIsSerial());
        object.setMaterialKenel(this.getMaterialKenel());
       object.setMaterialType(this.getMaterialType());
        object.setOutgoingTracking(this.getOutgoingTracking());
        object.setOutsourcingType(this.getOutsourcingType());
        object.setPlanningAttribute(this.getPlanningAttribute());
        object.setProductionSalesman(this.getProductionSalesman());
        object.setValuationMethod(this.getValuationMethod());
        object.setMaterialLevel(this.getMaterialLevel());
        object.setAgainBuyPlace(this.getAgainBuyPlace());
        object.setBuyer(this.getBuyer());
        object.setCreatePerson(this.getCreatePerson());
        object.setCreateTime(this.getCreateTime());
        object.setEnable(this.getEnable());
        object.setFixedAdvanceTime(this.getFixedAdvanceTime());
        object.setIsBatchesAccount(this.getIsBatchesAccount());
       object.setMinStock(this.getMinStock());
       object.setProductionDepts(this.getProductionDepts());
       object.setSafetyStock(this.getSafetyStock());
       object.setSealPerson(this.getSealPerson());
       object.setSealSign(this.getSealSign());
       object.setSealTime(this.getSealTime());
       object.setUpdatePerson(this.getUpdatePerson());
       object.setUpdateTime(this.getUpdateTime());
       object.setZhy(this.getZhy());
       object.setApprovalState(this.getApprovalState());
       object.setMaterialType(this.materialType);
       object.setEnable(this.enable);
    return  object;
    }









}