package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.RoleDao;
import org.navistack.admin.modules.common.dao.UserDao;
import org.navistack.admin.modules.common.dao.UserRoleDao;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.common.entity.UserRole;
import org.navistack.admin.modules.common.query.RoleQuery;
import org.navistack.admin.modules.common.query.UserLoginNameQuery;
import org.navistack.admin.modules.common.query.UserQuery;
import org.navistack.admin.modules.common.query.UserRoleQuery;
import org.navistack.admin.modules.mgmt.service.UserService;
import org.navistack.admin.modules.mgmt.service.convert.UserConverter;
import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.ModelMappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao dao;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao) {
        this.dao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public Page<UserDto> paginate(UserQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<User> entities = dao.selectWithPageable(query, pageable);
        Collection<UserDto> dtos = UserConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public UserDetailVm queryDetailById(Long id) {
        User user = dao.selectOneById(id);

        UserDetailVm vm = ModelMappers.map(user, UserDetailVm.class);

        UserRoleQuery urq = new UserRoleQuery();
        urq.setUserId(id);
        List<Long> roleIds = userRoleDao.select(urq)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        vm.setRoleIds(roleIds);

        return vm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserDto dto) {
        ensureUnique(dto);

        User entity = UserConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
        replaceRolesOf(entity.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(UserDto dto) {
        ensureUnique(dto);

        User entity = UserConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
        replaceRolesOf(entity.getId(), dto.getRoleIds());
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }

    protected void ensureUnique(UserDto dto) {
        UserLoginNameQuery query = new UserLoginNameQuery();
        query.setLoginName(dto.getLoginName());
        query.setMobileNumber(dto.getMobileNumber());
        query.setEmailAddress(dto.getEmailAddress());
        User existedOne = dao.selectOneByLoginName(query);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("User has existed");
    }

    protected void replaceRolesOf(Long userId, List<Long> roleIds) {
        UserRoleQuery urq = UserRoleQuery.builder()
                .userId(userId)
                .build();
        userRoleDao.delete(urq);

        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }

        for (Long roleId : roleIds) {
            RoleQuery rq = RoleQuery.builder()
                    .id(roleId)
                    .build();
            if (roleDao.count(rq) > 0) {
                UserRole entity = UserRole.of(userId, roleId);
                userRoleDao.insert(entity);
            }
        }
    }
}
