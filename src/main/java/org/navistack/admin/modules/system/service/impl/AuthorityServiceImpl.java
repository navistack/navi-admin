package org.navistack.admin.modules.system.service.impl;

import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.dao.RolePrivilegeDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.dtobj.PrivilegeDo;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.system.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
    public List<PrivilegeDo> listGrantedPrivilegesOf(UserDo user) {
        List<Long> userRoleIds = userRoleDao.selectAllRoleIdsByUserId(user.getId());
        if (userRoleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> privilegeIds = rolePrivilegeDao.selectAllPrivilegeIdsByRoleIds(userRoleIds);
        if (privilegeIds.isEmpty()) {
            return Collections.emptyList();
        }

        return privilegeDao.selectAllByIds(privilegeIds);
    }
}
