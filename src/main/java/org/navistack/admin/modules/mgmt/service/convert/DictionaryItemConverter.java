package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryItemDto;

import java.util.Collection;

@Mapper
public interface DictionaryItemConverter {
    DictionaryItemConverter INSTANCE = Mappers.getMapper(DictionaryItemConverter.class);

    DictionaryItem dtoToEntity(DictionaryItemDto dto);

    DictionaryItemDto entityToDto(DictionaryItem entity);

    Collection<DictionaryItemDto> entitiesToDtos(Collection<DictionaryItem> entities);
}
