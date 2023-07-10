package org.navistack.admin.modules.system.service.impl;

import org.navistack.admin.modules.identity.dao.UserDao;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.query.UserLoginNameQuery;
import org.navistack.admin.modules.system.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDao userDao;

    public AuthenticationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findUserByLoginName(String loginName) {
        return Optional.ofNullable(
                userDao.selectOneByLoginName(
                        UserLoginNameQuery.builder()
                                .loginName(loginName)
                                .emailAddress(loginName)
                                .mobileNumber(loginName)
                                .build()
                )
        );
    }
}
