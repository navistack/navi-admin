package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.RegionDo;
import org.navistack.admin.modules.common.service.dto.RegionDto;

@Mapper
public interface RegionDtoConvert {
    RegionDtoConvert INSTANCE = Mappers.getMapper(RegionDtoConvert.class);

    RegionDto from(RegionDo dtObj);
}
