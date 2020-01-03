package com.hqhop.modules.company.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.company.domain.EmployeeCompany;
import com.hqhop.modules.company.service.dto.EmployeeCompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zf
* @date 2020-01-02
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeCompanyMapper extends EntityMapper<EmployeeCompanyDTO, EmployeeCompany> {

}
