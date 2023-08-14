package org.navistack.admin.modules.system.service.impl;

import org.navistack.admin.modules.identity.dao.UserDao;
import org.navistack.admin.modules.identity.dtobj.UserDo;
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
    public Optional<UserDo> findUserByLoginName(String loginName) {
        return Optional.ofNullable(
                userDao.selectByLoginName(
                        UserLoginNameQuery.builder()
                                .loginName(loginName)
                                .emailAddress(loginName)
                                .mobileNumber(loginName)
                                .build()
                )
        );
    }
}
