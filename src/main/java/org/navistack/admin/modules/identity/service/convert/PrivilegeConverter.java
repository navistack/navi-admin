package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;

@Mapper
public interface PrivilegeConverter {
    PrivilegeConverter INSTANCE = Mappers.getMapper(PrivilegeConverter.class);

    PrivilegeDto toDto(Privilege entity);
}
