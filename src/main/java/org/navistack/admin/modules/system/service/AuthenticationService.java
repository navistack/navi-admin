package org.navistack.admin.modules.system.service;

import org.navistack.admin.modules.identity.dtobj.UserDo;

import java.util.Optional;

public interface AuthenticationService {
    /**
     * Find user by login name.
     *
     * @param loginName login name, mobile number, or email
     * @return user found, presented using {@link Optional}
     */
    Optional<UserDo> findUserByLoginName(String loginName);
}
