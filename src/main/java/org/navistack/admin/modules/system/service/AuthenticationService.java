package org.navistack.admin.modules.system.service;

import org.navistack.admin.modules.common.entity.User;

import java.util.Optional;

public interface AuthenticationService {
    /**
     * @param loginName login name, mobile number, or email
     * @return user found, presented using {@link Optional}
     */
    Optional<User> findUserByLoginName(String loginName);
}
