package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.service.dto.DictionaryDto;

import java.util.Collection;

@Mapper
public interface DictionaryDtoConverter {
    DictionaryDtoConverter INSTANCE = Mappers.getMapper(DictionaryDtoConverter.class);

    Dictionary toEntity(DictionaryDto dto);

    Collection<Dictionary> toEntities(Collection<DictionaryDto> dtos);
}
