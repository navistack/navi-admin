package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.RoleDo;
import org.navistack.admin.modules.identity.service.dto.RoleCreateDto;
import org.navistack.admin.modules.identity.service.dto.RoleModifyDto;

@Mapper
public interface RoleDoConvert {
    RoleDoConvert INSTANCE = Mappers.getMapper(RoleDoConvert.class);

    RoleDo from(RoleCreateDto dto);

    RoleDo from(RoleModifyDto dto);
}
