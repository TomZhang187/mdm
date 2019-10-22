package com.hqhop.modules.employee.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import me.zhengjie.modules.system.service.dto.DeptSmallDTO;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;


/**
* @author chengy
* @date 2019-10-21
*/
@Data
public class EmployeeDTO implements Serializable {

    // 主键id
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    // 身份证号
    private String employeeCardId;

    // 工号
    private String employeeCode;

    // 姓名
    private String employeeName;

    // 电话
    private String employeePhone;

    // 性别
    private String employeeSex;

    // 状态
    private Integer employeeState;

    //部门
    @ApiModelProperty(hidden = true)
    private List<DeptSmallDTO> depts;

    //角色
    @ApiModelProperty(hidden = true)
    private List<RoleSmallDTO> roles;
}