package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.admin.modules.identity.service.dto.RoleDto;
import org.navistack.admin.modules.identity.service.vm.RoleDetailVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface RoleService {
    Page<RoleDto> paginate(RoleQuery query, Pageable pageable);

    RoleDetailVm queryDetailById(Long roleId);

    void create(RoleDto dto);

    void modify(RoleDto dto);

    void remove(Long id);
}
