package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.dto.UserQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface UserService extends CrudService<UserDto, Long, UserQueryParams> {
    UserDetailVm queryDetailById(Long userId);
}
