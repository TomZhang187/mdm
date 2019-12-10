package com.hqhop.modules.material.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.material.domain.MaterialOperationRecord;
import com.hqhop.modules.material.service.dto.MaterialOperationRecordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zf
* @date 2019-12-09
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialOperationRecordMapper extends EntityMapper<MaterialOperationRecordDTO, MaterialOperationRecord> {

}
