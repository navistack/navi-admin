package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.dto.UserQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.framework.mybatisplusplus.CrudService;

public interface UserService extends CrudService<Long, User, UserDto, UserQueryParams> {
    UserDetailVm queryDetailById(Long userId);
}
