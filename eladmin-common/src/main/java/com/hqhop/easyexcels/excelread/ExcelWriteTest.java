package com.hqhop.easyexcels.excelread;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Table;
import lombok.Data;
import org.junit.Test;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

public class ExcelWriteTest {

    /**
     * 每行数据是List<String>无表头
     *
     * @throws IOException
     */
    @Test
    public void writeWithoutHead() throws IOException {
        try (OutputStream out = new FileOutputStream("D:/easyExcel/withoutHead.xlsx");) {

            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, false);
            Sheet sheet3 = new Sheet(1, 0);
            sheet3.setSheetName("sheet3");
            List<List<String>> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                List<String> item = new ArrayList<>();
                item.add("item0" + i);
                item.add("item1" + i);
                item.add("item2" + i);
                data.add(item);
            }
            writer.write0(data, sheet3);
            writer.finish();
        }
    }




    @Test
    public void writeHead() throws IOException {
        try (OutputStream out = new FileOutputStream("D:/easyExcel/ithHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("sheet1");
            List<List<String>> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                List<String> item = new ArrayList<>();
                item.add("item0" + i);
                item.add("item1" + i);
                item.add("item2" + i);
                data.add(item);
            }
            List<List<String>> head = new ArrayList<List<String>>();
            List<String> headCoulumn1 = new ArrayList<String>();
            List<String> headCoulumn2 = new ArrayList<String>();
            List<String> headCoulumn3 = new ArrayList<String>();
            headCoulumn1.add("第一列");
            headCoulumn2.add("第二列");
            headCoulumn3.add("第三列");
            head.add(headCoulumn1);
            head.add(headCoulumn2);
            head.add(headCoulumn3);
            Table table = new Table(1);
            table.setHead(head);
            writer.write0(data, sheet1, table);
            writer.finish();
        }
    }



    @Test
    public void writeWithHead() throws IOException {
        try (OutputStream out = new FileOutputStream("D:/easyExcel/withHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, ExcelPropertyIndexModel.class);
            sheet1.setSheetName("sheet1");
            List<ExcelPropertyIndexModel> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                ExcelPropertyIndexModel item = new ExcelPropertyIndexModel();
                item.name = "name" + i;
                item.age = "age" + i;
                item.email = "email" + i;
                item.address = "address" + i;
                item.sax = "sax" + i;
                item.heigh = "heigh" + i;
                item.last = "last" + i;
                data.add(item);
            }
            writer.write(data, sheet1);
            writer.finish();
        }
    }

    @Data
    public static class ExcelPropertyIndexModel extends BaseRowModel {

        @ExcelProperty(value = "姓名", index = 0)
        private String name;

        @ExcelProperty(value = "年龄", index = 1)
        private String age;

        @ExcelProperty(value = "邮箱", index = 2)
        private String email;

        @ExcelProperty(value = "地址", index = 3)
        private String address;

        @ExcelProperty(value = "性别", index = 4)
        private String sax;

        @ExcelProperty(value = "高度", index = 5)
        private String heigh;

        @ExcelProperty(value = "备注", index = 6)
        private String last;
    }










}



