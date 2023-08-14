package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.DictionaryItemDo;
import org.navistack.admin.modules.common.service.dto.DictionaryItemDto;

@Mapper
public interface DictionaryItemDtoConvert {
    DictionaryItemDtoConvert INSTANCE = Mappers.getMapper(DictionaryItemDtoConvert.class);

    DictionaryItemDto from(DictionaryItemDo dtObj);
}
