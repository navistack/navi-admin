package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.admin.modules.identity.service.dto.OrganizationDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface OrganizationService {
    Page<OrganizationDto> paginate(OrganizationQuery query, Pageable pageable);

    void create(OrganizationDto dto);

    void modify(OrganizationDto dto);

    void remove(Long id);
}
