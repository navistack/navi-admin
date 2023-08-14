package org.navistack.admin.modules.system.service;

import org.navistack.admin.modules.identity.dtobj.PrivilegeDo;
import org.navistack.admin.modules.identity.dtobj.UserDo;

import java.util.List;

public interface AuthorityService {
    List<PrivilegeDo> listGrantedPrivilegesOf(UserDo user);
}
