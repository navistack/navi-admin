package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.identity.service.dto.UserDto;

@Mapper
public interface UserDtoConvert {
    UserDtoConvert INSTANCE = Mappers.getMapper(UserDtoConvert.class);

    UserDto from(UserDo dtObj);
}
