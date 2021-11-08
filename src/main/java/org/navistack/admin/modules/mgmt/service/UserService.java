package org.navistack.admin.modules.mgmt.service;

import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.dto.UserQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

import java.util.Optional;

public interface UserService {

    Page<User> paginate(UserQueryParams queryParams, Pageable pageable);

    UserDetailVm queryDetailById(Long userId);

    void create(UserDto dto);

    void modify(UserDto dto);

    void remove(Long id);
}
