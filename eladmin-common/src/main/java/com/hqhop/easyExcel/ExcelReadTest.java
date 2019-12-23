package com.hqhop.easyExcel;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.hqhop.easyExcel.model.ExcelMode;
import com.hqhop.easyExcel.model.IncMaterialType;
import org.junit.Test;
import com.alibaba.excel.ExcelReader;
public class ExcelReadTest {


    @Test
    public void read() throws Exception {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("D:/easyExcel/withoutHead.xlsx"));) {
            AnalysisEventListener<List<String>> listener = new AnalysisEventListener<List<String>>() {

                @Override
                public void invoke(List<String> object, AnalysisContext context) {
                    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.err.println("doAfterAllAnalysed...");
                }
            };
            ExcelReader excelReader = ExcelReaderFactory.getExcelReader(in, null, listener);
            excelReader.read();
        }
    }


    @Test
    public void readTwo() throws Exception {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("D:/easyExcel/withoutHead.xlsx"));) {
            AnalysisEventListener<ExcelPropertyIndexModel> listener = new AnalysisEventListener<ExcelPropertyIndexModel>() {

                @Override
                public void invoke(ExcelPropertyIndexModel object, AnalysisContext context) {
                    System.out.println("调用");
                    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.err.println("doAfterAllAnalysed...");
                }
            };
            ExcelReader excelReader = ExcelReaderFactory.getExcelReader(in, null, listener);
            // 第二个参数为表头行数，按照实际设置
            excelReader.read(new Sheet(1, 1, ExcelPropertyIndexModel.class));
        }
    }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSax() {
            return sax;
        }

        public void setSax(String sax) {
            this.sax = sax;
        }

        public String getHeigh() {
            return heigh;
        }

        public void setHeigh(String heigh) {
            this.heigh = heigh;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        @Override
        public String toString() {
            return "ExcelPropertyIndexModel [name=" + name + ", age=" + age + ", email=" + email + ", address=" + address
                    + ", sax=" + sax + ", heigh=" + heigh + ", last=" + last + "]";
        }
    }

    // 简单读取 (同步读取)
    @Test
    public void simpleRead() {

        // 读取 excel 表格的路径
        String readPath = "C:\\Users\\oukele\\Desktop\\模拟数据.xlsx";
        String readPath2 = "D:\\easyExcel\\ithHead.xlsx";

        try {
            // sheetNo --> 读取哪一个 表单
            // headLineMun --> 从哪一行开始读取( 不包括定义的这一行，比如 headLineMun为2 ，那么取出来的数据是从 第三行的数据开始读取 )
            // clazz --> 将读取的数据，转化成对应的实体，需要 extends BaseRowModel
            Sheet sheet = new Sheet(1, 1, ExcelMode.class);

            // 这里 取出来的是 ExcelModel实体 的集合
            List<Object> readList = EasyExcelFactory.read(new FileInputStream(readPath2), sheet);
            // 存 ExcelMode 实体的 集合
            List<ExcelMode> list = new ArrayList<ExcelMode>();
            for (Object obj : readList) {
                list.add((ExcelMode) obj);
            }

            // 取出数据
            StringBuilder str = new StringBuilder();
            str.append("{");
            String link = "";
            for (ExcelMode mode : list) {
                str.append(link).append("\""+mode.getColumn1()+"\":").append("\""+mode.getColumn2()+"\"");
                link= ",";
            }
            str.append("};");
            System.out.println(str);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    // 异步读取
    @Test
    public void simpleRead1(){
        // 读取 excel 表格的路径
        String readPath = "C:\\Users\\oukele\\Desktop\\模拟数据.xlsx";
        String readPath2 = "D:\\easyExcel\\ithHead.xlsx";

        try {
            Sheet sheet = new Sheet(1,1,ExcelMode.class);
            EasyExcelFactory.readBySax(new FileInputStream(readPath2),sheet,new ExcelModelListener());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public  void readExcel(){
        try {

            String readPath2 = "D:\\easyExcel\\Testsdfasf.xlsx";
            //创建输入流
            InputStream inputStream = new FileInputStream(readPath2);

            Sheet sheet = new Sheet(1,3,IncMaterialType.class);

            List<Object> studentList = EasyExcelFactory.read(inputStream,sheet);
            List<IncMaterialType> students = new LinkedList<>();
            for (Object student : studentList){
                students.add((IncMaterialType)student);
            }
            for (IncMaterialType student : students){
                System.out.println(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
