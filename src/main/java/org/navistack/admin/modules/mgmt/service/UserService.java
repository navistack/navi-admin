package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.dto.UserQueryDto;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface UserService extends CrudService<UserDto, Long, UserQueryDto> {
    UserDetailVm queryDetailById(Long userId);
}
