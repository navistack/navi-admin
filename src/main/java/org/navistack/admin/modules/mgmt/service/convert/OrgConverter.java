package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Org;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;

import java.util.Collection;

@Mapper
public interface OrgConverter {
    OrgConverter INSTANCE = Mappers.getMapper(OrgConverter.class);

    Org dtoToEntity(OrgDto dto);

    OrgDto entityToDto(Org entity);

    Collection<OrgDto> entitiesToDtos(Collection<Org> entities);
}
