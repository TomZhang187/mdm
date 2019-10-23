package com.hqhop.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.domain.LocalStorage;
import com.hqhop.service.dto.LocalStorageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalStorageMapper extends EntityMapper<LocalStorageDTO, LocalStorage> {

}
