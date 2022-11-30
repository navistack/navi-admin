package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.query.DictQuery;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface DictService {
    Page<DictDto> paginate(DictQuery query, Pageable pageable);

    void create(DictDto dto);

    void modify(DictDto dto);

    void remove(Long id);

}
