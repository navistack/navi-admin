package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface PrivilegeService {
    Page<PrivilegeDto> paginate(PrivilegeQuery query, Pageable pageable);

    void create(PrivilegeDto dto);

    void modify(PrivilegeDto dto);

    void remove(Long id);
}
