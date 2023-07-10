package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Role;
import org.navistack.admin.modules.identity.service.dto.RoleDto;

import java.util.Collection;

@Mapper
public interface RoleConverter {
    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    Role dtoToEntity(RoleDto dto);

    RoleDto entityToDto(Role entity);

    Collection<RoleDto> entitiesToDtos(Collection<Role> entities);
}
