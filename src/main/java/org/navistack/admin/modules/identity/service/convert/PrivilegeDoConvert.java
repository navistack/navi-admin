package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.PrivilegeDo;
import org.navistack.admin.modules.identity.service.dto.PrivilegeCreateDto;
import org.navistack.admin.modules.identity.service.dto.PrivilegeModifyDto;

@Mapper
public interface PrivilegeDoConvert {
    PrivilegeDoConvert INSTANCE = Mappers.getMapper(PrivilegeDoConvert.class);

    PrivilegeDo from(PrivilegeCreateDto dto);

    PrivilegeDo from(PrivilegeModifyDto dto);
}
