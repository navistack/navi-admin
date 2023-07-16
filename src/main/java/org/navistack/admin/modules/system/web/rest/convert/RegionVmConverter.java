package org.navistack.admin.modules.system.web.rest.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;

import java.util.Collection;

@Mapper
public interface RegionVmConverter {
    RegionVmConverter INSTANCE = Mappers.getMapper(RegionVmConverter.class);

    RegionVm fromEntity(Region entity);

    Collection<RegionVm> fromEntities(Collection<Region> entities);
}
