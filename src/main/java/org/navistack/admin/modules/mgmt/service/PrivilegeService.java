package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeQueryParams;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface PrivilegeService {
    Page<Privilege> paginate(PrivilegeQueryParams queryParams, Pageable pageable);

    void create(PrivilegeDto dto);

    void modify(PrivilegeDto dto);

    void remove(Long id);
}
