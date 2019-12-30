package com.hqhop.easyexcel.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

//供应商档案表导入数据模型
@Data
public class IncSupplier extends BaseRowModel {


//    @ExcelProperty(value = "序号", index = 1)
//    private String order;
//
//
//    @ExcelProperty(value = "所属公司", index = 2)
//    private String t3.custom;


    @ExcelProperty(value = "客商编码", index = 0)
    private String customerCode;


    @ExcelProperty(value = "客商名称", index = 1)
    private String customerName;


    @ExcelProperty(value = "客商简称", index = 2)
    private String customerShortName;


    @ExcelProperty(value = "客商总公司编码", index = 3)
    private String parentCompanyCode;


    @ExcelProperty(value = "客商类型", index = 4)
    private String customerType;

    @ExcelProperty(value = "对应公司", index = 5)
    private String custom;


    @ExcelProperty(value = "所属地区", index = 6)
    private String belongArea;


    @ExcelProperty(value = "通信地址", index = 7)
    private String contactAddress;


    @ExcelProperty(value = "电话1", index = 8)
    private String phoneOne;


    @ExcelProperty(value = "联系人1", index = 9)
    private String contactOne;


    @ExcelProperty(value = "客商属性", index = 10)
    private String customerProperty;


    @ExcelProperty(value = "联系人", index = 11)
    private String contact;



    @ExcelProperty(value = "手机", index = 12)
    private String phone;


//   @ExcelProperty(value = "销售组织", index = 13)
//    private String chargeDepartment;
//
//    @ExcelProperty(value = "库存组织", index = 14)
//    private String chargeDepartment;

    @ExcelProperty(value = "专管部门(采购信息)", index = 15)
    private String chargeDepartment;


    @ExcelProperty(value = "专管业务员(采购信息)", index = 16)
    private String professionSalesman;


    @ExcelProperty(value = "专管部门(销售信息)", index =17 )
    private String defaultPaymentAgreement;

//    @ExcelProperty(value = "专管业务员(销售信息)", index = 18)
//    private String defaultPaymentAgreement;


}
