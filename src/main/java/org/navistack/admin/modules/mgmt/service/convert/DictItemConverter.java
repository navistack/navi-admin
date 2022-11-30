package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;

import java.util.Collection;

@Mapper
public interface DictItemConverter {
    DictItemConverter INSTANCE = Mappers.getMapper(DictItemConverter.class);

    DictItem dtoToEntity(DictItemDto dto);

    DictItemDto entityToDto(DictItem entity);

    Collection<DictItemDto> entitiesToDtos(Collection<DictItem> entities);
}
