package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.dto.RoleQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
import org.navistack.framework.crudsupport.CrudService;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface RoleService extends CrudService<Long, Role, RoleDto, RoleQueryParams> {
    RoleDetailVm queryDetailById(Long roleId);
}
