package com.hqhop.modules.company.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;


/**
* @author zf
* @date 2019-11-07
*/
@Data
public class ContactDTO implements Serializable {

    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long contactKey;

    private String contactAddress;

    private String email;

    private String contactName;

    private String phone;

    private String position;

    private Long companyKey;

    private Integer contactType;

    private String deliveryAddress;

    private Integer isDefaultAddress;
}