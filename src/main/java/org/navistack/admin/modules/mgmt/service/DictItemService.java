package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.admin.modules.mgmt.service.dto.DictItemQueryParams;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

import java.util.List;

public interface DictItemService {
    List<DictItem> list();

    Page<DictItem> paginate(DictItemQueryParams queryParams, Pageable pageable);

    void create(DictItemDto dto);

    void modify(DictItemDto dto);

    void remove(Long id);
}
