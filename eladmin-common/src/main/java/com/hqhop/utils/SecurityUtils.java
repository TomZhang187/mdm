package com.hqhop.utils;

import cn.hutool.json.JSONObject;
import com.hqhop.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 获取当前登录的用户
 * @author Zheng Jie
 * @date 2019-01-17
 */
public class SecurityUtils {

    public static UserDetails getUserDetails() {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        } catch (Exception e) {
                throw new BadRequestException(HttpStatus.UNAUTHORIZED, "登录状态过期");
        }
        return userDetails;
    }


    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    public static String getUsername(){
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("username", String.class);
    }


    /**
     * 获取真实员工名称
     * @return 真实员工名称
     */
    public static String getEmployeeName(){
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("employeeName", String.class);
    }


    /**
     * 获取当前用户钉钉iD
     * @return 获取当前用户钉钉iD
     */
    public static String getDingId(){
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("dingId", String.class);
    }

    /**
     * 获取系统用户id
     * @return 系统用户id
     */
    public static Long getUserId(){
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("id", Long.class);
    }


    /**
     * 获取系统员工id
     * @return 系统用户id
     */
    public static Long getEmployeeId(){
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("employeeId", Long.class);
    }


    /**
     * 获取系统员工工号
     * @return 系统用户id
     */
    public static String getEmployeeCode(){
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("employeeCode", String.class);
    }
}
