package com.hqhop.modules.system.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.system.domain.DictDetail;
import com.hqhop.modules.system.service.dto.DictDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends EntityMapper<DictDetailDTO, DictDetail> {

}
