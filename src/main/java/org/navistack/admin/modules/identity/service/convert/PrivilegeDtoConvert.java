package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.PrivilegeDo;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;

@Mapper
public interface PrivilegeDtoConvert {
    PrivilegeDtoConvert INSTANCE = Mappers.getMapper(PrivilegeDtoConvert.class);

    PrivilegeDto from(PrivilegeDo dtObj);
}
