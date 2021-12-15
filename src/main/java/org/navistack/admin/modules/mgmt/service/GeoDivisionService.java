package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.GeoDivision;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionDto;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionQueryParams;
import org.navistack.framework.crudsupport.CrudService;

public interface GeoDivisionService extends CrudService<Long, GeoDivision, GeoDivisionDto, GeoDivisionQueryParams> {
}
