package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.dto.RoleQueryDto;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface RoleService extends CrudService<RoleDto, Long, RoleQueryDto> {
    RoleDetailVm queryDetailById(Long roleId);
}
