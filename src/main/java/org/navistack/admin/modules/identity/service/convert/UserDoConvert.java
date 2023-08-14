package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.identity.service.dto.UserDto;

@Mapper
public interface UserDoConvert {
    UserDoConvert INSTANCE = Mappers.getMapper(UserDoConvert.class);

    @Mapping(target = "password", ignore = true)
    UserDo from(UserDto dto);
}
