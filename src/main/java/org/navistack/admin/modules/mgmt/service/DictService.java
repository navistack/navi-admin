package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.admin.modules.mgmt.service.dto.DictQueryDto;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface DictService extends CrudService<DictDto, Long, DictQueryDto> {
}
