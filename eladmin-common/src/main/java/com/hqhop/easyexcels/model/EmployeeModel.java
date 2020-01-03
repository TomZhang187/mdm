package com.hqhop.easyexcels.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class EmployeeModel extends BaseRowModel {


    @ExcelProperty(value = "员工UserID", index = 0)
    private String employeeId;

    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @ExcelProperty(value = "手机号", index = 2)
    private String phone;


    @ExcelProperty(value = "部门", index = 3)
    private String deptStr;

    @ExcelProperty(value = "职位", index = 4)
    private String position;


    @ExcelProperty(value = "工号", index = 5)
    private String JobNumber;


    @ExcelProperty(value = "是否此部门主管(是/否)", index = 6)
    private String isDirector;


    @ExcelProperty(value = "邮箱", index = 7)
    private String email;


    @ExcelProperty(value = "分机号", index = 8)
    private String extNumber;


    @ExcelProperty(value = "办公地点", index = 9)
    private String officeSpace;


    @ExcelProperty(value = "备注", index = 10)
    private String remark;


    @ExcelProperty(value = "入职时间", index = 11)
    private String entryTime;


    @ExcelProperty(value = "企业邮箱", index = 12)
    private String mail;

    @ExcelProperty(value = "激活状态", index = 13)
    private String enable;

    @ExcelProperty(value = "角色", index = 14)
    private String role;

}
