package org.navistack.admin.modules.system.web.rest.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryVm;

import java.util.Collection;

@Mapper
public interface DictionaryVmConverter {
    DictionaryVmConverter INSTANCE = Mappers.getMapper(DictionaryVmConverter.class);

    DictionaryVm fromEntity(Dictionary entity);

    Collection<DictionaryVm> fromEntities(Collection<Dictionary> entities);
}
