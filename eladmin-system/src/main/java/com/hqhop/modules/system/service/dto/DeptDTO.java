package com.hqhop.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class DeptDTO implements Serializable {

    /**
     * 名称
     */
    private String name;

    @NotNull
    private Boolean enabled;

    /**
     * 上级部门
     */
    private Long pid;

    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private Timestamp createTime;


    private String address;

    private String deptCode;

    private String deptDutyAndProperty;

    private String deptLevel;

    private String deptManagerUseridList;

    private String deptPhone;

    private String deptProperty;

    private String deptType;

    private String dingId;

    private Timestamp foundTime;

    private String inventoryOrganization;

    private Integer isRetail;

    private String mnemonicCode;

    private String remark;

    private Timestamp sealTime;

    private String shortName;

    private Long showOrder;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptDTO> children;


    public String getLabel() {
        return name;
    }
}
