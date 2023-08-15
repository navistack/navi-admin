package org.navistack.admin.modules.common.service;

import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.common.service.dto.DictionaryCreateDto;
import org.navistack.admin.modules.common.service.dto.DictionaryItemModifyDto;
import org.navistack.admin.modules.common.service.dto.DictionaryModifyDto;
import org.navistack.admin.modules.common.service.vm.DictionaryVm;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryItemVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface DictionaryService {
    Page<DictionaryVm> paginate(DictionaryQuery query, Pageable pageable);

    void create(DictionaryCreateDto dto);

    void modify(DictionaryModifyDto dto);

    void remove(Long id);

    Page<DictionaryItemVm> paginateItem(DictionaryItemQuery query, Pageable pageable);

    void createItem(DictionaryItemModifyDto dto);

    void modifyItem(DictionaryItemModifyDto dto);

    void removeItem(Long id);
}
