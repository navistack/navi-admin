package org.navistack.admin.modules.system.service.impl;

import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.dao.RolePrivilegeDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.entity.RolePrivilege;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.entity.UserRole;
import org.navistack.admin.modules.identity.query.UserRoleQuery;
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
        List<UserRole> userRoles = userRoleDao.selectAllByQuery(
                UserRoleQuery.builder()
                        .userId(user.getId())
                        .build()
        );

        List<Long> userRoleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        List<RolePrivilege> rolePrivileges = userRoles.isEmpty()
                ? Collections.emptyList()
                : rolePrivilegeDao.selectAllByRoleIds(userRoleIds);

        List<Long> privilegeIds = rolePrivileges.stream()
                .map(RolePrivilege::getPrivilegeId)
                .collect(Collectors.toList());

        return privilegeIds.isEmpty()
                ? Collections.emptyList()
                : privilegeDao.selectAllByIds(privilegeIds)
                ;
    }
}
