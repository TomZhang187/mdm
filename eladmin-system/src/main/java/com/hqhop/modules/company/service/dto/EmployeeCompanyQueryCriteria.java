package com.hqhop.modules.company.service.dto;

import lombok.Data;
import com.hqhop.annotation.Query;

/**
* @author zf
* @date 2020-01-02
*/
@Data
public class EmployeeCompanyQueryCriteria{

    // 精确
    @Query
    private Long employeesKey;

    // 精确
    @Query
    private Long companyKey;
}
