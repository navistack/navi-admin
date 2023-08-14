package org.navistack.admin.modules.identity.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.identity.dtobj.OrganizationDo;
import org.navistack.admin.modules.identity.service.dto.OrganizationDto;

@Mapper
public interface OrganizationDtoConvert {
    OrganizationDtoConvert INSTANCE = Mappers.getMapper(OrganizationDtoConvert.class);

    OrganizationDto from(OrganizationDo dtObj);
}
