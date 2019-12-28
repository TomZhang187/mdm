package com.hqhop.easyExcel.excelRead;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.hqhop.easyExcel.model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

//物料Excel工具类
public class MaterialExcelUtils {



    //物料类型excel读取工具
    public static List<IncMaterialType> readMaterialTypeExcel(String fileName) {

            //创建输入流
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,3,IncMaterialType.class);
            List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
            List<IncMaterialType> types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncMaterialType)student);
        }
            return  types;


    }


    //物料excel读取工具
    public static List<IncMaterial> readMaterialExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,5,IncMaterial.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<IncMaterial> types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncMaterial)student);
        }
        return  types;


    }
    //物料分类读取工具
    public static List<IncMaterialSort> readMaterialSortExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,1,IncMaterialSort.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<IncMaterialSort> types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncMaterialSort)student);
        }
        return  types;
    }









    //物料基本档案读取   物料整理.xlsx
    public static List<IncMaterialSort2 > readMaterialSort2Excel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,1,IncMaterialSort2.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<IncMaterialSort2 > types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncMaterialSort2 )student);
        }
        return  types;
    }


    //物料基本档案读取   物料整理总表2019-12-26-19-36(1).xlsx
    public static List<IncMateril2 > readMaterial2Sort2Excel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,1,IncMateril2.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<IncMateril2 > types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncMateril2 )student);
        }
        return  types;
    }


}
