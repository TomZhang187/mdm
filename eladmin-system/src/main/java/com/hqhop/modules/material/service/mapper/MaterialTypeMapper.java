package com.hqhop.modules.material.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.material.domain.MaterialType;
import com.hqhop.modules.material.service.dto.MaterialTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author KinLin
* @date 2019-10-30
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialTypeMapper extends EntityMapper<MaterialTypeDTO, MaterialType> {

}