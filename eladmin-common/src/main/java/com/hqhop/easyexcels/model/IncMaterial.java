package com.hqhop.easyexcels.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class IncMaterial  extends BaseRowModel {

    @ExcelProperty(value = "首位编码", index = 0)
    private String thefirstcode;


    @ExcelProperty(value = "大类", index = 1)
    private String bigClass;


    @ExcelProperty(value = "标识", index = 2)
    private String identification;


    @ExcelProperty(value = "存货分类", index = 3)
    private String stockClassification;

    @ExcelProperty(value = "新存货编码", index = 4)
    private String newCinvcode;


    @ExcelProperty(value = "原存货编码", index = 5)
    private String oldCinvcode;


    @ExcelProperty(value = "存货名称", index = 6)
    private String stockName;

    @ExcelProperty(value = "型号", index = 7)
    private String model;


    @ExcelProperty(value = "主计量单位", index = 8)
    private String mainUnitMeasurement;


    @ExcelProperty(value = "税目", index = 9)
    private String taxitems;


    @ExcelProperty(value = "是否应税劳务", index = 10)
    private String isDutiableService;

    @ExcelProperty(value = "创建人", index = 11)
    private String createPeron;


    @ExcelProperty(value = "创建时间", index = 12)
    private String createTime;


    @ExcelProperty(value = "修改人", index = 13)
    private String updateMan;


    @ExcelProperty(value = "修改日期", index = 14)
    private String updateTime;

    //物料生产档案内容




    @ExcelProperty(value = "出库跟踪入库", index = 15)
    private String isOutTrackWarehousing;


    @ExcelProperty(value = "需求管理", index = 16)
    private String outgoingTracking;

    @ExcelProperty(value = "是否需求管理", index = 17)
    private String isDemand;


    @ExcelProperty(value = "是否进行序列号管理", index = 18)
    private String isSerial;


    @ExcelProperty(value = "是否批次管理", index = 19)
    private String isBatchManagement;



    @ExcelProperty(value = "默认工厂", index = 21)
    private String defaultFactory;


    @ExcelProperty(value = "自定义项（物料类型）", index = 22)
    private String custom;


    @ExcelProperty(value = "是否封存", index = 23)
    private String enable;

    @ExcelProperty(value = "封存时间", index = 24)
    private String sealTime;




    @ExcelProperty(value = "物料类型", index = 31)
    private String materialType;

    @ExcelProperty(value = "委外类型", index = 32)
    private String outsourcingType;

    @ExcelProperty(value = "物料型态", index = 33)
    private String materialKenel;



    @ExcelProperty(value = "是否需求合并", index = 34)
    private String isDemandConsolidation;

    @ExcelProperty(value = "是否虚项", index = 35)
    private String isImaginaryTerm;



    @ExcelProperty(value = "是否成本对象", index = 36)
    private String isCostObject;


    @ExcelProperty(value = "是否发料", index = 37)
    private String isHairFeed;


    @ExcelProperty(value = "是否根据检验结果入库", index = 38)
    private String isInspectionWarehousing;


    @ExcelProperty(value = "是否免检", index = 39)
    private String isInspect;

    @ExcelProperty(value = "是否按生产订单核算成本", index = 40)
    private String isOrderCost;

    @ExcelProperty(value = "是否按成本中心统计产量", index = 41)
    private String isCenterStatistics;





    @ExcelProperty(value = "是否出入库", index = 45)
    private String isOutgoingWarehousing;

    @ExcelProperty(value = "是否批次核算", index = 46)
    private String  isBatchesAccount;


    @ExcelProperty(value = "计价方式", index = 47)
    private String  valuationMethod;


//    @ExcelProperty(value = "安全库存", index = 45)
//    private String  safetyStock;
//
//    @ExcelProperty(value = "最低库存", index = 46)
//    private String  minStock;
//
//    @ExcelProperty(value = "最高库存", index = 47)
//    private String  maxStock;


    @ExcelProperty(value = "计划属性", index = 54)
    private String  planningAttribute;

    @ExcelProperty(value = "固定提前期", index = 55)
    private String  fixedAdvanceTime;




}
