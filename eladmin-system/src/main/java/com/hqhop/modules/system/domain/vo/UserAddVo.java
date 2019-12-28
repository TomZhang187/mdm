package com.hqhop.modules.system.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：张丰
 * @date ：Created in 2019/12/3 0003 15:58
 * @description：用户新建表示层对象
 * @modified By：
 * @version: $
 */
public class UserAddVo {

    //用户名
    private String username;

    //状态
    private Boolean enabled;


    //所属部门集合字符格式
    private String  deptListStr;

//
//    //所属岗位集合字符格式
//    private String jobListStr;


    //所属角色集合字符格式
    private  String roleListStr;


   public List<Long> getListDepts(){
       if(deptListStr != null){
           String [] str = deptListStr.split(",");
           List<Long> list = new ArrayList<>();
           for (String s : str) {
               list.add(Long.valueOf(s));
           }
           return  list;
       }

       return  null;

   }

    public List<Long> getListRoles(){

       if(roleListStr != null){
           String [] str = roleListStr.split(",");
           List<Long> list = new ArrayList<>();
           for (String s : str) {
               list.add(Long.valueOf(s));
           }
           return  list;
       }
        return  null;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDeptListStr() {
        return deptListStr;
    }

    public void setDeptListStr(String deptListStr) {
        this.deptListStr = deptListStr;
    }

//    public String getJobListStr() {
//        return jobListStr;
//    }
//
//    public void setJobListStr(String jobListStr) {
//        this.jobListStr = jobListStr;
//    }

    public String getRoleListStr() {
        return roleListStr;
    }

    public void setRoleListStr(String roleListStr) {
        this.roleListStr = roleListStr;
    }
}
