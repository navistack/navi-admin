package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.query.OrgQuery;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface OrgService {
    Page<OrgDto> paginate(OrgQuery query, Pageable pageable);

    void create(OrgDto dto);

    void modify(OrgDto dto);

    void remove(Long id);
}
