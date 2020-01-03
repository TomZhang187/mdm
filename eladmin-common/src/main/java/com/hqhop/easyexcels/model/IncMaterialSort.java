package com.hqhop.easyexcels.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class IncMaterialSort extends BaseRowModel {



    @ExcelProperty(value = "标识", index = 0)
    private String thefirstcode;


    @ExcelProperty(value = "小类", index = 1)
    private String smallClass;


    @ExcelProperty(value = "大类", index = 2)
    private String bigClass;


    @ExcelProperty(value = "首位编码", index = 3)
    private String theFirstCode;


    @ExcelProperty(value = "流水码", index = 4)
    private String waterCode;


    @ExcelProperty(value = "新物料代码", index = 5)
    private String newCinvcode;


    @ExcelProperty(value = "原物料代码2", index = 6)
    private String oldCinvCode;

    @ExcelProperty(value = "图纸明细栏名称", index = 7)
    private String detailsName;


    @ExcelProperty(value = "图纸明细栏规格型号", index = 8)
    private String models;


    @ExcelProperty(value = "单位", index = 9)
    private String unit;


    @ExcelProperty(value = "属性01", index = 10)
    private String porperty01;

    @ExcelProperty(value = "属性02", index = 11)
    private String porperty02;


    @ExcelProperty(value = "属性3", index = 12)
    private String porperty3;


    @ExcelProperty(value = "属性4", index = 13)
    private String porperty4;


    @ExcelProperty(value = "属性5", index = 14)
    private String porperty5;


    @ExcelProperty(value = "属性6", index = 15)
    private String porperty6;


    @ExcelProperty(value = "属性7", index = 16)
    private String porperty7;

    @ExcelProperty(value = "属性8", index = 17)
    private String porperty8;


    @ExcelProperty(value = "属性9", index = 18)
    private String porperty9;


    @ExcelProperty(value = "属性1", index = 19)
    private String porperty1;


}
