package org.navistack.admin.modules.system.web.rest.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.RegionDo;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;

@Mapper
public interface RegionVmConverter {
    RegionVmConverter INSTANCE = Mappers.getMapper(RegionVmConverter.class);

    RegionVm from(RegionDo dtObj);
}
