package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.mgmt.service.dto.UserDto;

import java.util.Collection;

@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User dtoToEntity(UserDto dto);

    UserDto entityToDto(User entity);

    Collection<UserDto> entitiesToDtos(Collection<User> entities);
}
