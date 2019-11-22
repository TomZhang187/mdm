package com.hqhop.modules.company.service.dto;

import lombok.Data;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author zf
* @date 2019-11-06
*/
@Data
public class AccountDTO implements Serializable {

    // 账户主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long accountKey;

    // 账号
    private String account;

    // 开户行
    private String accountBlank;

    // 银行类型
    private Integer blankClass;

    // 币种
    private Integer currency;

    // 是否默认
    private Integer isDefalut;

    // 账户名
    private String name;

    private Long companyKey;
}