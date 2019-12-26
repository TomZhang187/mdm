package com.hqhop.easyExcel.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

//供应商档案表导入数据模型
@Data
public class IncSupplier  extends BaseRowModel {


    @ExcelProperty(value = "序号", index = 1)
    private String order;


    @ExcelProperty(value = "所属公司", index = 2)
    private String belongCompany;


    @ExcelProperty(value = "原K3系统编码", index = 3)
    private String oldk3Code;


    @ExcelProperty(value = "原K3名称", index = 4)
    private String  oldK3Name;



    @ExcelProperty(value = "收集人", index = 5)
    private String netMan;



    @ExcelProperty(value = "客商编码", index = 6)
    private String customerCode;



    @ExcelProperty(value = "客商名称", index = 7)
    private String customerName;


    @ExcelProperty(value = "客商简称", index = 8)
    private String customerShortName;


    @ExcelProperty(value = "客商总公司编码", index = 14)
    private String parentCompanyCode;


    @ExcelProperty(value = "客商类型", index = 15)
    private String customerType;




    @ExcelProperty(value = "所属地区", index = 17)
    private String belongArea;


    @ExcelProperty(value = "通信地址", index = 24)
    private String contactAddress;




    @ExcelProperty(value = "电话1", index = 26)
    private String phoneOne;


    @ExcelProperty(value = "联系人1", index = 31)
    private String  contactOne;


    @ExcelProperty(value = "客商属性", index = 42)
    private String  customerProperty;


    @ExcelProperty(value = "联系人", index = 44)
    private String contact;


    @ExcelProperty(value = "呼机", index = 45)
    private String pager;


    @ExcelProperty(value = "手机", index = 46)
    private String phone;



        @ExcelProperty(value = "专管部门(采购信息)", index = 60)
        private String chargeDepartment;


        @ExcelProperty(value = "专管业务员(采购信息)", index = 61)
        private String professionSalesman;


        @ExcelProperty(value = "默认收付款协议(采购信息)", index = 69)
        private String defaultPaymentAgreement;


}
