package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.PrivilegeDo;
import org.navistack.admin.modules.identity.service.vm.PrivilegeVm;

@Mapper
public interface PrivilegeVmConvert {
    PrivilegeVmConvert INSTANCE = Mappers.getMapper(PrivilegeVmConvert.class);

    PrivilegeVm from(PrivilegeDo dtObj);
}
