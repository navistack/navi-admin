package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.RegionDo;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;

@Mapper
public interface RegionVmConvert {
    RegionVmConvert INSTANCE = Mappers.getMapper(RegionVmConvert.class);

    RegionVm from(RegionDo dtObj);
}
