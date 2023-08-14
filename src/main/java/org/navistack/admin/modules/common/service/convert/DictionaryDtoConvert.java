package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.DictionaryDo;
import org.navistack.admin.modules.common.service.dto.DictionaryDto;

@Mapper
public interface DictionaryDtoConvert {
    DictionaryDtoConvert INSTANCE = Mappers.getMapper(DictionaryDtoConvert.class);

    DictionaryDto from(DictionaryDo dtObj);
}
