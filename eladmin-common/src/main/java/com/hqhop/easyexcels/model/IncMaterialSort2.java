package com.hqhop.easyexcels.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


//物料整理表读取物料基本档案
@Data
public class IncMaterialSort2  extends BaseRowModel {



    @ExcelProperty(value = "标识", index = 0)
    private String identification;

    @ExcelProperty(value = "小类", index = 1)
    private String smallClass;


    @ExcelProperty(value = "大类", index = 2)
    private String thefirstcode;


    @ExcelProperty(value = "首位编码", index = 3)
    private String firstCode;




    @ExcelProperty(value = "新物料代码", index = 4)
    private String newMaterialCode;

    @ExcelProperty(value = "原物料代码", index = 5)
    private String oldMaterialCode;


    @ExcelProperty(value = "图纸明细栏名称", index = 6)
    private String name;


    @ExcelProperty(value = "图纸明细栏规格型号", index = 7)
    private String model;

    @ExcelProperty(value = "单位", index = 8)
    private String unit;


    @ExcelProperty(value = "属性01", index = 9)
    private String property01;


    @ExcelProperty(value = "属性02", index = 10)
    private String property02;


    @ExcelProperty(value = "属性3", index = 11)
    private String property3;

    @ExcelProperty(value = "属性4", index = 12)
    private String property4;


    @ExcelProperty(value = "属性5", index = 13)
    private String property5;


    @ExcelProperty(value = "属性6", index = 14)
    private String property6;


    @ExcelProperty(value = "属性7", index = 15)
    private String property7;

    @ExcelProperty(value = "属性9", index = 16)
    private String property9;



    @ExcelProperty(value = "属性1", index = 17)
    private String property1;



    @ExcelProperty(value = "属性8", index = 18)
    private String property8;



    @ExcelProperty(value = "所属公司", index = 19)
    private String belognComany;



    @ExcelProperty(value = "新物料编码", index = 21)
    private String  newMaterialCode2;

}
