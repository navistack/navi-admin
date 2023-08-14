package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.RoleDo;
import org.navistack.admin.modules.identity.service.vm.RoleDetailVm;

@Mapper
public interface RoleDetailVmConvert {
    RoleDetailVmConvert INSTANCE = Mappers.getMapper(RoleDetailVmConvert.class);

    RoleDetailVm from(RoleDo dtObj);
}
