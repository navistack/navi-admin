package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.admin.modules.mgmt.service.dto.DictItemQueryParams;
import org.navistack.framework.crudsupport.CrudService;

public interface DictItemService extends CrudService<Long, DictItem, DictItemDto, DictItemQueryParams> {
}
