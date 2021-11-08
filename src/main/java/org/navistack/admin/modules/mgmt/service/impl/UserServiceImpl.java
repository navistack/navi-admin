package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.RoleDao;
import org.navistack.admin.modules.common.dao.UserDao;
import org.navistack.admin.modules.common.dao.UserRoleDao;
import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.common.entity.UserRole;
import org.navistack.admin.modules.common.enums.UserStatus;
import org.navistack.admin.modules.mgmt.service.UserService;
import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.dto.UserQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.admin.support.problems.EntityDuplicatedProblem;
import org.navistack.admin.utils.MyBatisPlusUtils;
import org.navistack.framework.core.utils.StaticModelMapper;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public Page<User> paginate(UserQueryParams queryParams, Pageable pageable) {
        String loginName = queryParams.getLoginName();
        String mobileNumber = queryParams.getMobileNumber();
        String emailAddress = queryParams.getEmailAddress();
        UserStatus status = queryParams.getStatus();

        Wrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .eq(loginName != null, User::getLoginName, loginName)
                .eq(mobileNumber != null, User::getMobileNumber, mobileNumber)
                .eq(emailAddress != null, User::getEmailAddress, emailAddress)
                .eq(status != null, User::getStatus, status);

        return MyBatisPlusUtils.PageUtils.toPage(
                userDao.selectPage(
                        MyBatisPlusUtils.PageUtils.fromPageable(pageable),
                        wrapper
                )
        );
    }

    @Override
    public UserDetailVm queryDetailById(Long userId) {
        User user = userDao.selectById(userId);

        UserDetailVm vm = StaticModelMapper.map(user, UserDetailVm.class);

        List<Long> roleIds = userRoleDao.selectList(
                        Wrappers.<UserRole>lambdaQuery()
                                .eq(UserRole::getUserId, userId)
                )
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        vm.setRoleIds(roleIds);

        return vm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserDto dto) {
        dto.setId(null);

        Long cnt = userDao.selectCount(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getEmailAddress, dto.getEmailAddress())
                        .or()
                        .and(w -> w
                                .eq(User::getMobilePrefix, dto.getMobilePrefix())
                                .eq(User::getMobileNumber, dto.getMobileNumber())
                        )
                        .or()
                        .eq(User::getLoginName, dto.getLoginName())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("User existed");
        }

        User user = StaticModelMapper.map(dto, User.class);
        userDao.insert(user);

        replaceRolesOf(user.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(UserDto dto) {
        Long cnt = userDao.selectCount(
                Wrappers.<User>lambdaQuery()
                        .and(w -> w
                                .eq(User::getEmailAddress, dto.getEmailAddress())
                                .or()
                                .and(ww -> ww
                                        .eq(User::getMobilePrefix, dto.getMobilePrefix())
                                        .eq(User::getMobileNumber, dto.getMobileNumber())
                                )
                                .or()
                                .eq(User::getLoginName, dto.getLoginName())
                        )
                        .ne(User::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("User existed");
        }

        User user = StaticModelMapper.map(dto, User.class);

        userDao.updateById(user);
        replaceRolesOf(user.getId(), dto.getRoleIds());
    }

    @Override
    public void remove(Long id) {
        userDao.deleteById(id);
    }

    protected void replaceRolesOf(Long userId, List<Long> roleIds) {
        userRoleDao.delete(
                Wrappers.<UserRole>lambdaQuery()
                        .eq(UserRole::getUserId, userId)
        );

        Optional.ofNullable(roleIds)
                .orElse(Collections.emptyList())
                .stream()
                .filter(roleId -> roleDao.selectCount(
                                Wrappers.<Role>lambdaQuery()
                                        .eq(Role::getId, roleId)
                        ) > 0
                )
                .map(roleId -> UserRole.of(userId, roleId))
                .forEach(userRoleDao::insert);
    }
}
