package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.admin.modules.identity.service.dto.UserDto;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface UserService {
    Page<UserDto> paginate(UserQuery query, Pageable pageable);

    UserDetailVm queryDetailById(Long userId);

    void create(UserDto dto);

    void modify(UserDto dto);

    void remove(Long id);
}
