package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.service.dto.DictionaryDto;

import java.util.Collection;

@Mapper
public interface DictionaryConverter {
    DictionaryConverter INSTANCE = Mappers.getMapper(DictionaryConverter.class);

    DictionaryDto toDto(Dictionary entity);

    Collection<DictionaryDto> toDtos(Collection<Dictionary> entities);
}
