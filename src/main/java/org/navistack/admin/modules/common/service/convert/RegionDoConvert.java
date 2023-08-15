package org.navistack.admin.modules.common.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.navistack.admin.modules.common.dtobj.RegionDo;
import org.navistack.admin.modules.common.service.dto.RegionCreateDto;
import org.navistack.admin.modules.common.service.dto.RegionModifyDto;

@Mapper
public interface RegionDoConvert {
    RegionDoConvert INSTANCE = Mappers.getMapper(RegionDoConvert.class);

    RegionDo from(RegionCreateDto dto);

    RegionDo from(RegionModifyDto dto);
}
