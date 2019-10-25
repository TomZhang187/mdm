package com.hqhop.modules.company.service.dto;

import lombok.Data;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author zf
* @date 2019-10-22
*/
@Data
public class ContactDTO implements Serializable {

    // 联系人主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long contactKey;

    // 联系地址
    private String contactAddress;

    //  邮件
    private String email;

    // 姓名
    private String name;

    // 电话
    private String phone;

    // 职务
    private String position;

    // 所属公司
    private Long companyKey;
}