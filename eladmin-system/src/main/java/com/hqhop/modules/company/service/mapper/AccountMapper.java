package com.hqhop.modules.company.service.mapper;

import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.company.domain.Account;
import com.hqhop.modules.company.service.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zf
* @date 2019-11-06
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper extends EntityMapper<AccountDTO, Account> {

}
