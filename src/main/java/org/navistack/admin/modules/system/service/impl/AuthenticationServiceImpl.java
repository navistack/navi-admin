package org.navistack.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.UserDao;
import org.navistack.admin.modules.common.entity.User;
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
                userDao.selectOne(
                        Wrappers.<User>lambdaQuery()
                                .nested(w -> w.eq(User::getLoginName, loginName)
                                        .or().eq(User::getEmailAddress, loginName)
                                        .or().eq(User::getMobileNumber, loginName)
                                )
                )
        );
    }
}
