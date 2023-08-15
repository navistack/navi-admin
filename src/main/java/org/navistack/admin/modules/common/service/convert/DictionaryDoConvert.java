package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.DictionaryDo;
import org.navistack.admin.modules.common.service.dto.DictionaryCreateDto;
import org.navistack.admin.modules.common.service.dto.DictionaryModifyDto;

@Mapper
public interface DictionaryDoConvert {
    DictionaryDoConvert INSTANCE = Mappers.getMapper(DictionaryDoConvert.class);

    DictionaryDo from(DictionaryCreateDto dto);

    DictionaryDo from(DictionaryModifyDto dto);
}
