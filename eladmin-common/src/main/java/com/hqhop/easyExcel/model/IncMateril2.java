package com.hqhop.easyExcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class IncMateril2  extends BaseRowModel {


    @ExcelProperty(value = "默认工厂", index = 0)
    private String line0;


    @ExcelProperty(value = "存货名称型号", index = 1)
    private String line1;


    @ExcelProperty(value = "首位编码", index = 2)
    private String line2;


    @ExcelProperty(value = "标识", index = 3)
    private String line3;

    @ExcelProperty(value = "大类", index = 4)
    private String line4;


    @ExcelProperty(value = "小类", index = 5)
    private String line5;


    @ExcelProperty(value = "新物料编码", index = 6)
    private String line6;

    @ExcelProperty(value = "首位编码", index = 7)
    private String line7;


    @ExcelProperty(value = "标识", index = 8)
    private String line8;


    @ExcelProperty(value = "大类", index = 9)
    private String line9;


    @ExcelProperty(value = "新存货编码", index = 10)
    private String line10;

    @ExcelProperty(value = "原存货编码", index = 11)
    private String line11;


    @ExcelProperty(value = "存货名称", index = 12)
    private String line12;


    @ExcelProperty(value = "型号", index = 13)
    private String line13;


    @ExcelProperty(value = "主计量单位", index = 14)
    private String line14;

    //物料生产档案内容

    @ExcelProperty(value = "税目", index = 15)
    private String line15;


    @ExcelProperty(value = "是否应税劳务", index = 16)
    private String line16;

    @ExcelProperty(value = "出库跟踪入库", index = 17)
    private String line17;


    @ExcelProperty(value = "需求管理", index = 18)
    private String line18;


    @ExcelProperty(value = "是否需求管理", index = 19)
    private String line19;



    @ExcelProperty(value = "是否进行序列号管理", index = 20)
    private String line20;


    @ExcelProperty(value = "是否批次管理", index = 21)
    private String line21;


    @ExcelProperty(value = "是否虚项", index = 22)
    private String line22;

    @ExcelProperty(value = "自定义项", index = 23)
    private String line23;


    @ExcelProperty(value = "自定义项", index = 24)
    private String line24;

    @ExcelProperty(value = "物料类型", index = 25)
    private String line25;

    @ExcelProperty(value = "委外类型", index = 26)
    private String line26;

    @ExcelProperty(value = "物料型态", index = 27)
    private String line27;



    @ExcelProperty(value = "是否需求合并", index = 28)
    private String line28;

    @ExcelProperty(value = "是否虚项", index = 29)
    private String line29;



    @ExcelProperty(value = "是否成本对象", index = 30)
    private String line30;


    @ExcelProperty(value = "是否发料", index = 31)
    private String line31;


    @ExcelProperty(value = "是否根据检验结果入库", index = 32)
    private String line32;


    @ExcelProperty(value = "是否免检", index = 33)
    private String line33;

    @ExcelProperty(value = "是否按生产订单核算成本", index = 34)
    private String line34;

    @ExcelProperty(value = "是否按成本中心统计产量", index = 35)
    private String line35;



    @ExcelProperty(value = "是否出入库", index = 36)
    private String line36;

    @ExcelProperty(value = "是否批次核算", index = 37)
    private String  line37;


    @ExcelProperty(value = "计价方式", index = 38)
    private String  line38;


    @ExcelProperty(value = "安全库存", index = 39)
    private String  line39;

    @ExcelProperty(value = "最低库存", index = 40)
    private String  line40;

    @ExcelProperty(value = "最高库存", index = 41)
    private String line41;


    @ExcelProperty(value = "再订购点", index = 42)
    private String  line42;


    @ExcelProperty(value = "计划属性", index = 43)
    private String   line43;

    @ExcelProperty(value = "固定提前期", index = 44)
    private String  line44;

    @ExcelProperty(value = "货位", index = 45)
    private String  line45;




}
