package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.query.PrivilegeQuery;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface PrivilegeService {
    Page<PrivilegeDto> paginate(PrivilegeQuery query, Pageable pageable);

    void create(PrivilegeDto dto);

    void modify(PrivilegeDto dto);

    void remove(Long id);
}
