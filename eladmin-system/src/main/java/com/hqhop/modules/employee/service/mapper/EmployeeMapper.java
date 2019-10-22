package com.hqhop.modules.employee.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import com.hqhop.modules.employee.domain.Employee;
import com.hqhop.modules.employee.service.dto.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author chengy
* @date 2019-10-21
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

}