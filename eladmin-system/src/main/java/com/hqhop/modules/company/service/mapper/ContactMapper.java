package com.hqhop.modules.company.service.mapper;

import com.hqhop.mapper.EntityMapper;

import com.hqhop.modules.company.domain.Contact;
import com.hqhop.modules.company.service.dto.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zf
* @date 2019-11-07
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

}
