package com.hqhop.easyexcels.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;

//客商excel读取
@Data
public class IncCustomer extends BaseRowModel {





    // 客商名称
    @ExcelProperty(value = "客商名称", index = 0)
    private String companyName;



    // 客商简称
    @ExcelProperty(value = "客商简称", index = 1)
    private String companyShortName;

    // 税务登记号 /客商编码
    @ExcelProperty(value = " 税务登记号", index = 2)
    private String taxId;

    // 所属地区
    @ExcelProperty(value = "所属地区", index = 3)
    private String belongArea;

    // 所属公司
    @ExcelProperty(value = "所属公司", index = 4)
    private String belongCompany;

    // 公司属性
    @ExcelProperty(value = "所属公司", index = 5)
    private String companyProp;


    // 客商状态
    @ExcelProperty(value = " 客商状态", index = 6)
    private String companyState;

    // 公司类型
    @ExcelProperty(value = " 公司类型", index = 7)
    private String companyType;



    //客商类型
    @ExcelProperty(value = "客商类型", index = 8)
    private String customerType;



    // 通讯地址
    @ExcelProperty(value = "通讯地址", index = 9)
    private String contactAddress;

    // 经济类型
    @ExcelProperty(value = "经济类型", index = 10)
    private String economicType;

    // 外文名称
    @ExcelProperty(value = "外文名称", index = 11)
    private String foreignName;

    // 是否禁用
    @ExcelProperty(value = "是否禁用", index = 12)
    private String isDisable;

    // 是否散户
    @ExcelProperty(value = "是否散户", index = 13)
    private String isRetai;

    // 法人
    @ExcelProperty(value = "法人", index = 14)
    private String legalbody;

    // 总公司编码
    @ExcelProperty(value = "总公司编码", index = 15)
    private Long parentCompanyId=0L;

    // 邮政编码
    @ExcelProperty(value = "邮政编码", index = 16)
    private String postalCode;

    // 注册资金
    @ExcelProperty(value = "注册资金", index = 17)
    private BigDecimal registerfund;

    // 备注
    @ExcelProperty(value = "备注", index = 18)
    private String remark;



    // 所属行业
    @ExcelProperty(value = "所属行业", index = 19)
    private String trade;



    //是否协同付款
    @ExcelProperty(value = "是否协同付款", index = 20)
    private String isSynergyPay;


    //专管部门
    @ExcelProperty(value = "专管部门", index = 21)
    private String chargeDepartment;

    //专管业务员
    @ExcelProperty(value = "专管业务员", index = 22)
    private String professionSalesman;

    //信用等级
    @ExcelProperty(value = "信用等级", index = 23)
    private String creditRating;

    //默认收款协议
    @ExcelProperty(value = "默认收款协议", index = 24)
    private String defaultPaymentAgreement;

    //创建人
    @ExcelProperty(value = "创建人", index = 25)
    private String createMan;

    //创建时间
    @ExcelProperty(value = "创建时间", index = 26)
    private String createTime;


    // 批准时间
    @ExcelProperty(value = "批准时间", index = 27)
    private String approveTime;

    //修改人
    @ExcelProperty(value = "修改人", index = 28)
    private String updateMan;


    //修改时间
    @ExcelProperty(value = "修改时间", index = 29)
    private String updateTime;






}
