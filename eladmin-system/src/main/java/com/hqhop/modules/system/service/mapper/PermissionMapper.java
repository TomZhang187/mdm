package com.hqhop.modules.system.service.mapper;

import com.hqhop.modules.system.domain.Permission;
import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.system.service.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
