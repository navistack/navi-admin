package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;

import java.util.Collection;

@Mapper
public interface PrivilegeDtoConverter {
    PrivilegeDtoConverter INSTANCE = Mappers.getMapper(PrivilegeDtoConverter.class);

    Privilege toEntity(PrivilegeDto dto);

    Collection<Privilege> toEntities(Collection<PrivilegeDto> dtos);
}
