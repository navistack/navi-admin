package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeQueryParams;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface PrivilegeService extends CrudService<Long, Privilege, PrivilegeDto, PrivilegeQueryParams> {
}
