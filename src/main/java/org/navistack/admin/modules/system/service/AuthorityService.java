package org.navistack.admin.modules.system.service;

import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.entity.User;

import java.util.List;

public interface AuthorityService {
    List<Privilege> listGrantedPrivilegesOf(User user);
}
