package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;

import java.util.Collection;

@Mapper
public interface PrivilegeConverter {
    PrivilegeConverter INSTANCE = Mappers.getMapper(PrivilegeConverter.class);

    Privilege dtoToEntity(PrivilegeDto dto);

    PrivilegeDto entityToDto(Privilege entity);

    Collection<PrivilegeDto> entitiesToDtos(Collection<Privilege> entities);
}
