package com.hqhop.modules.company.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.company.domain.CompanyBasic;
import com.hqhop.modules.company.service.dto.CompanyBasicDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lyl
* @date 2020-01-01
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyBasicMapper extends EntityMapper<CompanyBasicDTO, CompanyBasic> {

}
