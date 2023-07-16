package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.service.dto.RegionDto;

import java.util.Collection;

@Mapper
public interface RegionDtoConverter {
    RegionDtoConverter INSTANCE = Mappers.getMapper(RegionDtoConverter.class);

    Region toEntity(RegionDto dto);

    Collection<Region> toEntities(Collection<RegionDto> dtos);
}
