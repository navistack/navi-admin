package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.admin.modules.mgmt.service.dto.DictQueryParams;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

import java.util.List;

public interface DictService {
    List<Dict> list();

    Page<Dict> paginate(DictQueryParams queryParams, Pageable pageable);

    void create(DictDto dto);

    void modify(DictDto dto);

    void remove(Long id);
}
