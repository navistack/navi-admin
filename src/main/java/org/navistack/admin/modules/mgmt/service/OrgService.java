package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Org;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;
import org.navistack.admin.modules.mgmt.service.dto.OrgQueryParams;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface OrgService extends CrudService<Long, Org, OrgDto, OrgQueryParams> {
}
