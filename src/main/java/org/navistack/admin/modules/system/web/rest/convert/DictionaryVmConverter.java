package org.navistack.admin.modules.system.web.rest.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.DictionaryDo;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryVm;

@Mapper
public interface DictionaryVmConverter {
    DictionaryVmConverter INSTANCE = Mappers.getMapper(DictionaryVmConverter.class);

    DictionaryVm from(DictionaryDo dtObj);
}
