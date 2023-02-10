package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryDto;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryItemDto;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface DictionaryService {
    Page<DictionaryDto> paginate(DictionaryQuery query, Pageable pageable);

    void create(DictionaryDto dto);

    void modify(DictionaryDto dto);

    void remove(Long id);

    Page<DictionaryItemDto> paginateItem(DictionaryItemQuery query, Pageable pageable);

    void createItem(DictionaryItemDto dto);

    void modifyItem(DictionaryItemDto dto);

    void removeItem(Long id);
}
