package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface RegionService {
    Page<RegionDto> paginate(RegionQuery query, Pageable pageable);

    void create(RegionDto dto);

    void modify(RegionDto dto);

    void remove(Long id);
}
