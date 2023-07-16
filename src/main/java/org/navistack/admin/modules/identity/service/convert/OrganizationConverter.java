package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.entity.Organization;
import org.navistack.admin.modules.identity.service.dto.OrganizationDto;

import java.util.Collection;

@Mapper
public interface OrganizationConverter {
    OrganizationConverter INSTANCE = Mappers.getMapper(OrganizationConverter.class);

    OrganizationDto toDto(Organization entity);

    Collection<OrganizationDto> toDtos(Collection<Organization> entities);
}
