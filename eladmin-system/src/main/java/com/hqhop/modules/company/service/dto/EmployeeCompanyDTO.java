package com.hqhop.modules.company.service.dto;

import lombok.Data;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author zf
* @date 2020-01-02
*/
@Data
public class EmployeeCompanyDTO implements Serializable {

    // 职员主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long employeesKey;

    // 客商主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long companyKey;
}