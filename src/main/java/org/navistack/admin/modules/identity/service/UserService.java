package org.navistack.admin.modules.identity.service;

import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.admin.modules.identity.service.dto.UserCreateDto;
import org.navistack.admin.modules.identity.service.dto.UserModifyDto;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;
import org.navistack.admin.modules.identity.service.vm.UserVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;

public interface UserService {
    Page<UserVm> paginate(UserQuery query, Pageable pageable);

    UserDetailVm queryDetailById(Long userId);

    void create(UserCreateDto dto);

    void modify(UserModifyDto dto);

    void remove(Long id);
}
