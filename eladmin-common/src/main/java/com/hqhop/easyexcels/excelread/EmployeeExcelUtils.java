package com.hqhop.easyexcels.excelread;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.hqhop.easyexcels.model.EmployeeModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

//员工表excel表读取方法类
public class EmployeeExcelUtils {


    //客户档案读取工具
    public static List<EmployeeModel> readEmployeeExcel(String fileName) {

        //创建输入流
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sheet sheet = new Sheet(1,1,EmployeeModel.class);
        List<Object> typeList = EasyExcelFactory.read(inputStream,sheet);
        List<EmployeeModel> types = new LinkedList<>();
        for (Object student :  typeList){
            types.add((EmployeeModel)student);
        }
        return  types;
    }
}
