package com.hqhop.modules.material.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.material.domain.MaterialProduction;
import com.hqhop.modules.material.service.dto.MaterialProductionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author wst
* @date 2019-11-26
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialProductionMapper extends EntityMapper<MaterialProductionDTO, MaterialProduction> {

}
