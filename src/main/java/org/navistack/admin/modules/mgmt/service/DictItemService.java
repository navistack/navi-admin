package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.query.DictItemQuery;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface DictItemService {
    Page<DictItemDto> paginate(DictItemQuery query, Pageable pageable);

    void create(DictItemDto dto);

    void modify(DictItemDto dto);

    void remove(Long id);
}
