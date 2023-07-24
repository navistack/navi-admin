package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.service.dto.UserDto;

@Mapper
public interface UserDtoConverter {
    UserDtoConverter INSTANCE = Mappers.getMapper(UserDtoConverter.class);

    User toEntity(UserDto dto);
}
