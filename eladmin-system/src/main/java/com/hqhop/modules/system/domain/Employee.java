package com.hqhop.modules.system.domain;

import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hqhop.easyExcel.model.EmployeeModel;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
* @author zf
* @date 2019-11-28
*/
@Entity
@Data
@Table(name="employee")
public class Employee implements Serializable {

    // 员工主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    // 工号
    @Column(name = "employee_code")
    private String employeeCode;

    // 姓名
    @Column(name = "employee_name")
    private String employeeName;

    // 电话
    @Column(name = "employee_phone")
    private String employeePhone;

    //钉钉格式所属部门
    @Column(name = "ding_belong_depts")
    private String dingBelongDepts;

    //页面显示格式所属部门
    @Column(name = "page_belong_depts")
    private String pageBelongDepts;

    // U8C部门编码
    @Column(name = "ubac_dept_code")
    private String ubacDeptCode;

    // U8C部门名
    @Column(name = "ubac_dept_name")
    private String ubacDeptName;

    // 是否主管
    @Column(name = "leader")
    private Boolean leader;

    // 邮箱
    @Column(name = "email")
    private String email;

    // 分机号
    @Column(name = "extension_number")
    private String extensionNumber;

    // 办公地址
    @Column(name = "office_address")
    private String officeAddress;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 状态
    @Column(name = "enabled")
    private Boolean enabled=true;

    // 公司邮箱
    @Column(name = "company_email")
    private String companyEmail;

    // 钉钉ID
    @Column(name = "ding_id")
    private String dingId;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(name = "employees_depts", joinColumns = {@JoinColumn(name = "employee_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "dept_id",referencedColumnName = "id")})
    private Set<Dept> depts;




    public void copy(Employee source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
    public static   List<Long> getDeptListByDing(String depts) {
        if(depts != null ){
            List<Long>  list = new ArrayList<>();
            List list1 = JSONArray.parseArray(depts);
            for (Object o : list1) {
                list.add(Long.valueOf(o.toString()));
            }
            return  list;
        }
       return  null;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeCode='" + employeeCode + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeePhone='" + employeePhone + '\'' +
                ", dingBelongDepts='" + dingBelongDepts + '\'' +
                ", pageBelongDepts='" + pageBelongDepts + '\'' +
                ", ubacDeptCode='" + ubacDeptCode + '\'' +
                ", ubacDeptName='" + ubacDeptName + '\'' +
                ", leader=" + leader +
                ", email='" + email + '\'' +
                ", extensionNumber='" + extensionNumber + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", remark='" + remark + '\'' +
                ", enabled=" + enabled +
                ", companyEmail='" + companyEmail + '\'' +
                ", dingId='" + dingId + '\'' +
                '}';
    }

    public @interface Update {}

    public Set<Long> getDeptsSet(){
        if(depts != null){
            Set<Long> list = new HashSet<>();
            for (Dept dept : depts) {
                list.add(dept.getId());
            }
            return  list;
        }
        return  null;
    }


    public Set<Dept> getDepts() {
        return depts;
    }

    public void setDepts(Set<Dept> depts) {
        this.depts = depts;
    }

    public void getDateByResponse(OapiUserListbypageResponse.Userlist dingUser){
         this.dingId = dingUser.getUserid();
         this.employeePhone = dingUser.getMobile();
         this.extensionNumber = dingUser.getTel();
         this.officeAddress = dingUser.getWorkPlace();
         this.remark = dingUser.getRemark();
         this.leader = dingUser.getIsLeader();
         this.employeeName = dingUser.getName();
         this.email = dingUser.getEmail();
         this.companyEmail = dingUser.getOrgEmail();
         this.employeeCode = dingUser.getJobnumber();
         this.dingBelongDepts = dingUser.getDepartment();
    }

    public void getDateByResponse(OapiUserGetResponse dingUser){
        this.dingId = dingUser.getUserid();
        this.employeePhone = dingUser.getMobile();
        this.extensionNumber = dingUser.getTel();
        this.officeAddress = dingUser.getWorkPlace();
        this.remark = dingUser.getRemark();
        this.employeeName = dingUser.getName();
        this.email = dingUser.getEmail();
        this.companyEmail = dingUser.getOrgEmail();
        this.employeeCode = dingUser.getJobnumber();
        if(dingUser.getDepartment() != null){
            this.dingBelongDepts = dingUser.getDepartment().toString();
        }

    }
    //从excel表读取对象拿数据
    public  void getDataByEmployeeModel(EmployeeModel model){
        this.dingId = model.getEmployeeId();
        this.pageBelongDepts = model.getDeptStr();
        this.employeePhone = model.getPhone();
        this.employeeCode = model.getJobNumber();
        this.leader = "是".equals(model.getIsDirector())?true:false;
        this.email = model.getEmail();
        this.employeeName = model.getName();
        this.remark = model.getRemark();
        this.officeAddress = model.getOfficeSpace();
        this.enabled = "是".equals(model.getEnable())?true:false;
         this.extensionNumber = model.getExtNumber();


    }




    }
