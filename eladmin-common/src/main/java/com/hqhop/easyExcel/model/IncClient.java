package com.hqhop.easyExcel.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

//客户档案表对应读取数据模型
@Data
public class IncClient extends BaseRowModel {




    @ExcelProperty(value = "所属公司", index = 0)
    private String belongCompany;


    @ExcelProperty(value = "K3代码", index = 1)
    private String k3Code;


    @ExcelProperty(value = "客商编码", index = 2)
    private String  customerCode;



    @ExcelProperty(value = "客商名称", index = 3)
    private String customerNmae;



    @ExcelProperty(value = "客商简称", index = 4)
    private String customerShortName;


    @ExcelProperty(value = "客商总公司编码", index = 5)
    private String parentCompanyCode;


    @ExcelProperty(value = "客商类型", index = 6)
    private String customerType;



    @ExcelProperty(value = "所属地区", index = 7)
    private String belongArea;




    @ExcelProperty(value = "经济类型", index = 8)
    private String economyType;


    @ExcelProperty(value = "备注", index = 9)
    private String remark;

    @ExcelProperty(value = "客商属性", index = 10)
    private String customerProperty;

    @ExcelProperty(value = "通信地址", index = 11)
    private String contactAddress;




}
