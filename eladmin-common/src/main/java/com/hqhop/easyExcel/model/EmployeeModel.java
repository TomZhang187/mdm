package com.hqhop.easyExcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class EmployeeModel extends BaseRowModel {


    @ExcelProperty(value = "员工UserID", index = 0)
    private String employeeId;

}
