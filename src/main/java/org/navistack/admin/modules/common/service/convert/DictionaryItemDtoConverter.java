package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.service.dto.DictionaryItemDto;

@Mapper
public interface DictionaryItemDtoConverter {
    DictionaryItemDtoConverter INSTANCE = Mappers.getMapper(DictionaryItemDtoConverter.class);

    DictionaryItem toEntity(DictionaryItemDto dto);
}
