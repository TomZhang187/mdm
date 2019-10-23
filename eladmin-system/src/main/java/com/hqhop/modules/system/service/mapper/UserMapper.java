package com.hqhop.modules.system.service.mapper;

import com.hqhop.modules.system.domain.User;
import com.hqhop.mapper.EntityMapper;
import com.hqhop.modules.system.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",uses = {RoleMapper.class, DeptMapper.class, JobMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(source = "user.userAvatar.realName",target = "avatar")
    UserDTO toDto(User user);
}
