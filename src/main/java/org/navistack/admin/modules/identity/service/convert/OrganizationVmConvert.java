package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.OrganizationDo;
import org.navistack.admin.modules.identity.service.vm.OrganizationVm;

@Mapper
public interface OrganizationVmConvert {
    OrganizationVmConvert INSTANCE = Mappers.getMapper(OrganizationVmConvert.class);

    OrganizationVm from(OrganizationDo dtObj);
}
