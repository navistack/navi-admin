package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.admin.modules.identity.service.dto.OrganizationCreateDto;
import org.navistack.admin.modules.identity.service.dto.OrganizationModifyDto;
import org.navistack.admin.modules.identity.service.vm.OrganizationVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface OrganizationService {
    Page<OrganizationVm> paginate(OrganizationQuery query, Pageable pageable);

    void create(OrganizationCreateDto dto);

    void modify(OrganizationModifyDto dto);

    void remove(Long id);
}
