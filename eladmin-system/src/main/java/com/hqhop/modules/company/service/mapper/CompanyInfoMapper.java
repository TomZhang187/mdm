package com.hqhop.modules.company.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.company.domain.CompanyInfo;
import com.hqhop.modules.company.service.dto.CompanyInfoDTO;;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zf
* @date 2019-10-22
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyInfoMapper extends EntityMapper<CompanyInfoDTO, CompanyInfo> {

}