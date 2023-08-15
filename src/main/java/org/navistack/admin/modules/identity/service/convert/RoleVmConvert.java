package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.RoleDo;
import org.navistack.admin.modules.identity.service.vm.RoleVm;

@Mapper
public interface RoleVmConvert {
    RoleVmConvert INSTANCE = Mappers.getMapper(RoleVmConvert.class);

    RoleVm from(RoleDo dtObj);
}
