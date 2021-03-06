package com.hqhop.modules.system.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Data
public class UserQueryCriteria implements Serializable {

    @Query
    private Long id;

    @Query(propName = "id", type = Query.Type.IN, joinName = "dept")
    private Set<Long> deptIds;

    @Query(propName = "id", type = Query.Type.IN, joinName = "employee")
    private Set<Long> employeeIds;

    // 多字段模糊
    @Query(blurry = "email,username")
    private String blurry;


    private Long deptId;

    @Query
    private Boolean enabled;


}
