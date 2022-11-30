package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;

import java.util.Collection;

@Mapper
public interface PrivilegeConverter {
    PrivilegeConverter INSTANCE = Mappers.getMapper(PrivilegeConverter.class);

    Privilege dtoToEntity(PrivilegeDto dto);

    PrivilegeDto entityToDto(Privilege entity);

    Collection<PrivilegeDto> entitiesToDtos(Collection<Privilege> entities);
}
