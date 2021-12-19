package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;
import org.navistack.admin.modules.mgmt.service.dto.RegionQueryParams;
import org.navistack.framework.crudsupport.CrudService;

public interface RegionService extends CrudService<Long, Region, RegionDto, RegionQueryParams> {
}
