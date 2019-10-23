package com.hqhop.modules.system.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.system.domain.Dept;
import com.hqhop.modules.system.service.dto.DeptDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends EntityMapper<DeptDTO, Dept> {

}
