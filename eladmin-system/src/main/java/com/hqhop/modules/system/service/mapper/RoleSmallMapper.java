package com.hqhop.modules.system.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.system.domain.Role;
import com.hqhop.modules.system.service.dto.RoleSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2019-5-23
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends EntityMapper<RoleSmallDTO, Role> {

}
