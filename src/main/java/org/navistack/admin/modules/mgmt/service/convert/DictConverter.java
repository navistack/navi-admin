package org.navistack.admin.modules.mgmt.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;

import java.util.Collection;

@Mapper
public interface DictConverter {
    DictConverter INSTANCE = Mappers.getMapper(DictConverter.class);

    Dict dtoToEntity(DictDto dto);

    DictDto entityToDto(Dict entity);

    Collection<DictDto> entitiesToDtos(Collection<Dict> entities);
}
