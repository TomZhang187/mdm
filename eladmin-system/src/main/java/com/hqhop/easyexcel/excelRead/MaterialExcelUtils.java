package com.hqhop.easyexcel.excelRead;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.hqhop.easyexcel.ExcelUtil;
import com.hqhop.easyexcel.model.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

//物料Excel工具类
public class MaterialExcelUtils {


    //物料类型excel读取工具
    public static List<IncMaterialType> readMaterialTypeExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;
        List<IncMaterialType> types = new LinkedList<>();
        try {
            inputStream = new FileInputStream(fileName);
            Sheet sheet = new Sheet(1, 3, IncMaterialType.class);
            List<Object> typeList = EasyExcelFactory.read(inputStream, sheet);

            for (Object student : typeList) {
                types.add((IncMaterialType) student);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return types;


    }


    //物料excel读取工具
    public static List readMaterialExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;
        List<IncMaterial> types = new LinkedList<>();
        try {
            inputStream = new FileInputStream(fileName);
            System.out.println("========读取开始=======");
            Sheet sheet = new Sheet(1, 1, IncMaterial.class);

            BufferedInputStream bis = new BufferedInputStream(inputStream);

            ExcelUtil.ExcelListener excelListener = new ExcelUtil.ExcelListener();
//            EasyExcelFactory.readBySax(inputStream, sheet, excelListener);
//            List<Object> typeList = excelListener.getDatas();
//            List typeList = EasyExcelFactory.read(inputStream, sheet);
//            ExcelUtil.readLessThan1000RowBySheet()
            List<Object> objects = ExcelUtil.readMoreThan1000RowBySheet(fileName, sheet);
            System.out.println("============读取结束==k=============");
            return objects;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;


    }

    //物料分类读取工具
    public static List<IncMaterialSort> readMaterialSortExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;
        List<IncMaterialSort> types = new LinkedList<>();
        try {
            inputStream = new FileInputStream(fileName);
            Sheet sheet = new Sheet(1, 1, IncMaterialSort.class);
            List<Object> typeList = EasyExcelFactory.read(inputStream, sheet);

            for (Object student : typeList) {
                types.add((IncMaterialSort) student);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return types;
    }


    //物料基本档案读取   物料整理.xlsx
    public static List<IncMaterialSort2> readMaterialSort2Excel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1, 1, IncMaterialSort2.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream, sheet);
        List<IncMaterialSort2> types = new LinkedList<>();
        for (Object student : typeList) {
            types.add((IncMaterialSort2) student);
        }
        return types;
    }


    //物料基本档案读取   物料整理总表2019-12-26-19-36(1).xlsx
    public static List<IncMateril2> readMaterial2Sort2Excel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1, 1, IncMateril2.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream, sheet);
        List<IncMateril2> types = new LinkedList<>();
        for (Object student : typeList) {
            types.add((IncMateril2) student);
        }
        return types;
    }


}
