package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Role;
import org.navistack.admin.modules.identity.service.dto.RoleDto;

@Mapper
public interface RoleDtoConverter {
    RoleDtoConverter INSTANCE = Mappers.getMapper(RoleDtoConverter.class);

    Role toEntity(RoleDto dto);
}
