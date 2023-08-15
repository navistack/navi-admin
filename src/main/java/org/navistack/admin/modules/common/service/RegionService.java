package org.navistack.admin.modules.common.service;

import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.common.service.dto.RegionCreateDto;
import org.navistack.admin.modules.common.service.dto.RegionModifyDto;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface RegionService {
    Page<RegionVm> paginate(RegionQuery query, Pageable pageable);

    void create(RegionCreateDto dto);

    void modify(RegionModifyDto dto);

    void remove(Long id);
}
