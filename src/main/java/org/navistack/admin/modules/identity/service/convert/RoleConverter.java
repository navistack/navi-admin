package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Role;
import org.navistack.admin.modules.identity.service.dto.RoleDto;

import java.util.Collection;

@Mapper
public interface RoleConverter {
    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    RoleDto toDto(Role entity);

    Collection<RoleDto> toDtos(Collection<Role> entities);
}
