package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.admin.modules.identity.service.dto.PrivilegeCreateDto;
import org.navistack.admin.modules.identity.service.dto.PrivilegeModifyDto;
import org.navistack.admin.modules.identity.service.vm.PrivilegeVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface PrivilegeService {
    Page<PrivilegeVm> paginate(PrivilegeQuery query, Pageable pageable);

    void create(PrivilegeCreateDto dto);

    void modify(PrivilegeModifyDto dto);

    void remove(Long id);
}
