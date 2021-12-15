package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.admin.modules.mgmt.service.dto.DictQueryParams;
import org.navistack.framework.crudsupport.CrudService;

public interface DictService extends CrudService<Long, Dict, DictDto, DictQueryParams> {
}
