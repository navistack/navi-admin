package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.GeoDivision;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionDto;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionQueryParams;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

import java.util.List;

public interface GeoDivisionService {
    List<GeoDivision> list();

    Page<GeoDivision> paginate(GeoDivisionQueryParams queryParams, Pageable pageable);

    void create(GeoDivisionDto dto);

    void modify(GeoDivisionDto dto);

    void remove(Long id);
}
