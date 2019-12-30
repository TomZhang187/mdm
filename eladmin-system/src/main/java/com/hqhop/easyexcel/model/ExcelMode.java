package com.hqhop.easyexcel.model;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 表格实体
 */
@Data
public class ExcelMode extends BaseRowModel {

    /**
     * 第一列的数据
     */
    @ExcelProperty(index = 0)
    private String column1;
    /**
     * 第二列的数据
     */
    @ExcelProperty(index = 1)
    private String column2;

}
