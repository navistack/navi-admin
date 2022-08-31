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
import org.navistack.admin.modules.mgmt.service.dto.UserQueryDto;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.mybatisplusplus.AbstractCrudService;
import org.navistack.framework.utils.ModelMappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl
        extends AbstractCrudService<User, Long, UserDto, UserQueryDto, UserDao>
        implements UserService {
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao) {
        super(userDao);
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    protected Wrapper<User> buildWrapper(UserQueryDto queryDto) {
        String loginName = queryDto.getLoginName();
        String mobileNumber = queryDto.getMobileNumber();
        String emailAddress = queryDto.getEmailAddress();
        UserStatus status = queryDto.getStatus();

        return Wrappers.<User>lambdaQuery()
                .eq(loginName != null, User::getLoginName, loginName)
                .eq(mobileNumber != null, User::getMobileNumber, mobileNumber)
                .eq(emailAddress != null, User::getEmailAddress, emailAddress)
                .eq(status != null, User::getStatus, status);
    }

    @Override
    public UserDetailVm queryDetailById(Long userId) {
        User user = dao.selectById(userId);

        UserDetailVm vm = ModelMappers.map(user, UserDetailVm.class);

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

        boolean existing = dao.exists(
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

        if (existing) {
            throw new EntityDuplicationException("User existed");
        }

        User user = ModelMappers.map(dto, User.class);
        dao.insert(user);

        replaceRolesOf(user.getId(), dto.getRoleIds());

        ModelMappers.map(user, dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(UserDto dto) {
        boolean existing = dao.exists(
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

        if (existing) {
            throw new EntityDuplicationException("User existed");
        }

        User user = ModelMappers.map(dto, User.class);

        dao.updateById(user);
        replaceRolesOf(user.getId(), dto.getRoleIds());
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
