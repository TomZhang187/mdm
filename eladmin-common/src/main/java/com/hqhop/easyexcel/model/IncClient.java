package com.hqhop.easyExcel.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

//客户档案表对应读取数据模型
@Data
public class IncClient extends BaseRowModel {


    @ExcelProperty(value = "序号", index = 0)
    private String order;


    @ExcelProperty(value = "所属公司", index = 1)
    private String belongCompany;


    @ExcelProperty(value = "K3代码", index = 2)
    private String k3Code;


    @ExcelProperty(value = "客商编码", index = 3)
    private String  customerCode;



    @ExcelProperty(value = "客商名称", index = 4)
    private String customerNmae;



    @ExcelProperty(value = "客商简称", index = 5)
    private String customerShortName;


    @ExcelProperty(value = "客商总公司编码", index = 11)
    private String parentCompanyCode;


    @ExcelProperty(value = "客商类型", index = 12)
    private String customerType;


    @ExcelProperty(value = "对应公司", index = 13)
    private String  correspondenceCompany;



    @ExcelProperty(value = "所属地区", index = 14)
    private String belongArea;




    @ExcelProperty(value = "经济类型", index = 17)
    private String economyType;


    @ExcelProperty(value = "法人", index = 18)
    private String legalPerson;


    @ExcelProperty(value = "价格分组", index = 19)
    private String priceGroup;


    @ExcelProperty(value = "备注", index = 20)
    private String remark;

    @ExcelProperty(value = "通信地址", index = 21)
    private String contactAddress;

    @ExcelProperty(value = "客商属性", index = 39)
    private String customerProperty;


}
