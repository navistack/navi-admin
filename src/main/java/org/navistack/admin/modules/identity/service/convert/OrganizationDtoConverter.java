package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Organization;
import org.navistack.admin.modules.identity.service.dto.OrganizationDto;

@Mapper
public interface OrganizationDtoConverter {
    OrganizationDtoConverter INSTANCE = Mappers.getMapper(OrganizationDtoConverter.class);

    Organization toEntity(OrganizationDto dto);
}
