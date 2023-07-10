package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.RoleDao;
import org.navistack.admin.modules.identity.dao.UserDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.entity.UserRole;
import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.admin.modules.identity.query.UserRoleQuery;
import org.navistack.admin.modules.identity.service.UserService;
import org.navistack.admin.modules.identity.service.convert.UserConverter;
import org.navistack.admin.modules.identity.service.dto.UserDto;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.Asserts;
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
        Asserts.state(dto.getLoginName(), dto.getId(), this::validateAvailabilityOfLoginName, () -> new DomainValidationException("Login name has been taken already"));
        Asserts.state(dto.getMobileNumber(), dto.getId(), this::validateAvailabilityOfMobileNumber, () -> new DomainValidationException("Mobile number has been taken already"));
        Asserts.state(dto.getEmailAddress(), dto.getId(), this::validateAvailabilityOfEmailAddress, () -> new DomainValidationException("Email address has been taken already"));

        User entity = UserConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
        replaceRolesOf(entity.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(UserDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("User does not exist"));
        Asserts.state(dto.getLoginName(), dto.getId(), this::validateAvailabilityOfLoginName, () -> new DomainValidationException("Login name has been taken already"));
        Asserts.state(dto.getMobileNumber(), dto.getId(), this::validateAvailabilityOfMobileNumber, () -> new DomainValidationException("Mobile number has been taken already"));
        Asserts.state(dto.getEmailAddress(), dto.getId(), this::validateAvailabilityOfEmailAddress, () -> new DomainValidationException("Email address has been taken already"));


        User entity = UserConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
        replaceRolesOf(entity.getId(), dto.getRoleIds());
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById, () -> new NoSuchEntityException("User does not exist"));

        dao.deleteById(id);

        UserRoleQuery urq = UserRoleQuery.builder()
                .userId(id)
                .build();
        userRoleDao.delete(urq);
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

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.selectOneById(id) != null;
    }

    protected boolean validateAvailabilityOfLoginName(String loginName, Long modifiedId) {
        UserQuery query = UserQuery.builder()
                .loginName(loginName)
                .build();
        User existingOne = dao.selectOne(query);

        if (existingOne == null) {
            return true;
        }

        if (existingOne.getId().equals(modifiedId)) {
            return true;
        }

        return false;
    }

    protected boolean validateAvailabilityOfMobileNumber(String mobileNumber, Long modifiedId) {
        UserQuery query = UserQuery.builder()
                .mobileNumber(mobileNumber)
                .build();
        User existingOne = dao.selectOne(query);

        if (existingOne == null) {
            return true;
        }

        if (existingOne.getId().equals(modifiedId)) {
            return true;
        }

        return false;
    }

    protected boolean validateAvailabilityOfEmailAddress(String emailAddress, Long modifiedId) {
        UserQuery query = UserQuery.builder()
                .emailAddress(emailAddress)
                .build();
        User existingOne = dao.selectOne(query);

        if (existingOne == null) {
            return true;
        }

        if (existingOne.getId().equals(modifiedId)) {
            return true;
        }

        return false;
    }

    // endregion
}
