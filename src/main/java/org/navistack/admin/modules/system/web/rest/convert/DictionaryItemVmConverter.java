package org.navistack.admin.modules.system.web.rest.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.DictionaryItemDo;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryItemVm;

@Mapper
public interface DictionaryItemVmConverter {
    DictionaryItemVmConverter INSTANCE = Mappers.getMapper(DictionaryItemVmConverter.class);

    DictionaryItemVm from(DictionaryItemDo dtObj);
}
