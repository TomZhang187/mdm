package com.hqhop.easyExcel.excelread;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.hqhop.easyExcel.model.IncClient;
import com.hqhop.easyExcel.model.IncCustomer;
import com.hqhop.easyExcel.model.IncSupplier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


//客商读取Excel工具
public class CustomerExcelUtils {

    //客户档案读取工具
    public static List<IncClient> readClitentExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,3,IncClient.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<IncClient> types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncClient)student);
        }
        return  types;
    }

    //供应商档案读取工具
    public static List<IncSupplier> readSupplierExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,3,IncSupplier .class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<IncSupplier > types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncSupplier )student);
        }
        return  types;
    }



    //客商档案读取工具
    public static List<IncCustomer> readIncCustomer(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,1,IncCustomer .class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<IncCustomer > types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((IncCustomer )student);
        }
        return  types;
    }


}
