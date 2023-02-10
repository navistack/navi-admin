package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryDto;

import java.util.Collection;

@Mapper
public interface DictionaryConverter {
    DictionaryConverter INSTANCE = Mappers.getMapper(DictionaryConverter.class);

    Dictionary dtoToEntity(DictionaryDto dto);

    DictionaryDto entityToDto(Dictionary entity);

    Collection<DictionaryDto> entitiesToDtos(Collection<Dictionary> entities);
}
