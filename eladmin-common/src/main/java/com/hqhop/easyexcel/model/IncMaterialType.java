package com.hqhop.easyExcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
@Data
public class IncMaterialType extends BaseRowModel {



    @ExcelProperty(value = "序号", index = 0)
    private String id;


    @ExcelProperty(value = "大类编码", index = 1)
    private String bigCode;



    @ExcelProperty(value = "大类", index = 2)
    private String bigClass;



    @ExcelProperty(value = "存货分类编码", index = 3)
    private String materialTypeCode;


    @ExcelProperty(value = "流水码", index = 4)
    private String waterCode;


    @ExcelProperty(value = "存货分类名称", index = 5)
    private String materialClassName;



    @ExcelProperty(value = "备注", index = 6)
    private String remark;



}
