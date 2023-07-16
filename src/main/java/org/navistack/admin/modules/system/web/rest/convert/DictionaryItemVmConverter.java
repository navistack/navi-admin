package org.navistack.admin.modules.system.web.rest.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryItemVm;

import java.util.Collection;

@Mapper
public interface DictionaryItemVmConverter {
    DictionaryItemVmConverter INSTANCE = Mappers.getMapper(DictionaryItemVmConverter.class);

    DictionaryItemVm fromEntity(DictionaryItem entity);

    Collection<DictionaryItemVm> fromEntities(Collection<DictionaryItem> entities);
}
