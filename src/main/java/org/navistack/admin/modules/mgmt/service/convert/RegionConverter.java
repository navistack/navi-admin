package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;

import java.util.Collection;

@Mapper
public interface RegionConverter {
    RegionConverter INSTANCE = Mappers.getMapper(RegionConverter.class);

    Region dtoToEntity(RegionDto dto);

    RegionDto entityToDto(Region entity);

    Collection<RegionDto> entitiesToDtos(Collection<Region> entities);
}
