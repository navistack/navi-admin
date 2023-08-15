package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.admin.modules.identity.service.dto.RoleCreateDto;
import org.navistack.admin.modules.identity.service.dto.RoleModifyDto;
import org.navistack.admin.modules.identity.service.vm.RoleDetailVm;
import org.navistack.admin.modules.identity.service.vm.RoleVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface RoleService {
    Page<RoleVm> paginate(RoleQuery query, Pageable pageable);

    RoleDetailVm queryDetailById(Long roleId);

    void create(RoleCreateDto dto);

    void modify(RoleModifyDto dto);

    void remove(Long id);
}
