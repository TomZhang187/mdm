package com.hqhop.modules.company.utils;

import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.taobao.api.ApiException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：张丰
 * @date ：Created in 2019/11/16 0016 20:10
 * @description：客商工具类
 * @modified By：
 * @version: $
 */
public class CompanyUtils {


    public static String removeTrim(String str){
        if(str.indexOf(".") > 0){
            str = str.replaceAll("0+?$", "");//去掉多余的0
            str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return str;
    }








}
