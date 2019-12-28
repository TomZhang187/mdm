package com.hqhop.modules.system.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

import javax.persistence.Column;

/**
* @author zf
* @date 2019-11-28
*/
@Data
public class EmployeeQueryCriteria{

    // 精确
    @Query
    private Long id;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String employeeCode;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String employeeName;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String employeePhone;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String ubacDeptCode;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String ubacDeptName;

    // 精确
    @Query
    private Boolean leader;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String email;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String extensionNumber;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String officeAddress;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;

    // 精确
    @Query
    private Boolean enabled;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String companyEmail;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String dingBelongDepts;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String pageBelongDepts;
}
