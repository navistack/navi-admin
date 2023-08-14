package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.RoleDo;
import org.navistack.admin.modules.identity.service.dto.RoleDto;

@Mapper
public interface RoleDtoConvert {
    RoleDtoConvert INSTANCE = Mappers.getMapper(RoleDtoConvert.class);

    RoleDto from(RoleDo dtObj);
}
