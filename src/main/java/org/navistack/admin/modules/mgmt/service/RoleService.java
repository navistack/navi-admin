package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.dto.RoleQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface RoleService {
    Page<Role> paginate(RoleQueryParams queryParams, Pageable pageable);

    RoleDetailVm queryDetailById(Long roleId);

    void create(RoleDto dto);

    void modify(RoleDto dto);

    void remove(Long id);
}
