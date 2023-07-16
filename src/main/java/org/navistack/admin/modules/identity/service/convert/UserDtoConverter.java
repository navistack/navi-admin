package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.service.dto.UserDto;

import java.util.Collection;

@Mapper
public interface UserDtoConverter {
    UserDtoConverter INSTANCE = Mappers.getMapper(UserDtoConverter.class);

    User toEntity(UserDto dto);

    Collection<User> toEntities(Collection<UserDto> dtos);
}
