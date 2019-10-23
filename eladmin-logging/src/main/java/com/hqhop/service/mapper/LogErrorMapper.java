package com.hqhop.service.mapper;

import com.hqhop.domain.Log;
import com.hqhop.mapper.EntityMapper;
import com.hqhop.service.dto.LogErrorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2019-5-22
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends EntityMapper<LogErrorDTO, Log> {

}
