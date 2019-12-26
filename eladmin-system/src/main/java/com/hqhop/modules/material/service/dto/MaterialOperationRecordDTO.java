package com.hqhop.modules.material.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author zf
* @date 2019-12-09
*/
@Data
public class MaterialOperationRecordDTO implements Serializable {

    // 主键
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long key;


    // 审批状态
    private String approvalState;

    // 审批结果
    private String approveResult;

    // 审批时间
    private Timestamp approveTime;

    // 创建人
    private String createPerson;

    //记录创建人
    private String creator;

    // 创建时间
    private Timestamp createTime;


    // 默认工厂
    private String defaultFactory;

    // 审批链接
    private String dingUrl;

    // 使用状态
    private Boolean enable;
    // 档案主键
    private Long id;

    // 规格型号
    private String model;

    // 存货名称
    private String name;

    // 操作类型
    private String operationType;


    // 审批实例ID
    private String processId;


    // 钉钉ID
    private String userId;

}