package com.hqhop.easyexcel;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/18 0018 9:18
 * @description：读取Excel的示例
 * @modified By：
 * @version: $
 */

import java.io.*;
import java.util.List;

import com.alibaba.excel.EasyExcelFactory;
import org.junit.Test;

import com.alibaba.excel.metadata.Sheet;

import static cn.hutool.core.io.FileUtil.getInputStream;

public class ExcelReadTest {


    @Test
    public void readExcel() {
        // 读取 excel 表格的路径
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("D:/1.xlsx")));
            List<Object> data = EasyExcelFactory.read(bis, new Sheet(1, 0));
            data.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
