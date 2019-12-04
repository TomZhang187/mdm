package com.hqhop.modules.company.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.company.domain.CompanyUpdate;
import com.hqhop.modules.company.service.dto.CompanyUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zf
* @date 2019-11-07
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyUpdateMapper extends EntityMapper<CompanyUpdateDTO, CompanyUpdate> {

}
