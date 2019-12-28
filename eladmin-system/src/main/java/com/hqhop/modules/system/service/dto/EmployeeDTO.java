package com.hqhop.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Column;


/**
* @author zf
* @date 2019-11-28
*/
@Data
public class EmployeeDTO implements Serializable {

    // 员工主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    // 工号
    private String employeeCode;

    // 姓名
    private String employeeName;

    // 电话
    private String employeePhone;

    //所属部门
    private String belongDepts;


    // U8C部门编码
    private String ubacDeptCode;

    // U8C部门名
    private String ubacDeptName;

    // 是否主管
    private Boolean iLeader;

    // 邮箱
    private String email;

    // 分机号
    private String extensionNumber;

    // 办公地址
    private String officeAddress;

    // 备注
    private String remark;

    // 状态
    private Boolean enabled;

    // 公司邮箱
    private String companyEmail;

    // 钉钉ID
    private String dingId;
}