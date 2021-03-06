package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.admin.modules.mgmt.service.dto.DictItemQueryParams;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface DictItemService extends CrudService<DictItemDto, Long, DictItemQueryParams> {
}
