package org.navistack.admin.modules.system.service.impl;

import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.dao.RolePrivilegeDao;
import org.navistack.admin.modules.common.dao.UserRoleDao;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.common.entity.RolePrivilege;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.common.entity.UserRole;
import org.navistack.admin.modules.common.query.UserRoleQuery;
import org.navistack.admin.modules.system.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final UserRoleDao userRoleDao;
    private final RolePrivilegeDao rolePrivilegeDao;
    private final PrivilegeDao privilegeDao;

    public AuthorityServiceImpl(UserRoleDao userRoleDao,
                                RolePrivilegeDao rolePrivilegeDao,
                                PrivilegeDao privilegeDao) {
        this.userRoleDao = userRoleDao;
        this.rolePrivilegeDao = rolePrivilegeDao;
        this.privilegeDao = privilegeDao;
    }

    @Override
    public List<Privilege> listGrantedPrivilegesOf(User user) {
        List<UserRole> userRoles = userRoleDao.select(
                UserRoleQuery.builder()
                        .userId(user.getId())
                        .build()
        );

        List<Long> userRoleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        List<RolePrivilege> rolePrivileges = userRoles.isEmpty()
                ? Collections.emptyList()
                : rolePrivilegeDao.selectByRoleIdIn(userRoleIds);

        List<Long> privilegeIds = rolePrivileges.stream()
                .map(RolePrivilege::getPrivilegeId)
                .collect(Collectors.toList());

        return privilegeIds.isEmpty()
                ? Collections.emptyList()
                : privilegeDao.selectByIds(privilegeIds)
                ;
    }
}
