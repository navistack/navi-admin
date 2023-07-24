package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.service.dto.DictionaryDto;

@Mapper
public interface DictionaryConverter {
    DictionaryConverter INSTANCE = Mappers.getMapper(DictionaryConverter.class);

    DictionaryDto toDto(Dictionary entity);
}
