package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.service.dto.DictionaryItemDto;

@Mapper
public interface DictionaryItemConverter {
    DictionaryItemConverter INSTANCE = Mappers.getMapper(DictionaryItemConverter.class);

    DictionaryItemDto toDto(DictionaryItem entity);
}
