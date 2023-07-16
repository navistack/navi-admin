package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.service.dto.RegionDto;

import java.util.Collection;

@Mapper
public interface RegionConverter {
    RegionConverter INSTANCE = Mappers.getMapper(RegionConverter.class);

    RegionDto toDto(Region entity);

    Collection<RegionDto> toDtos(Collection<Region> entities);
}
