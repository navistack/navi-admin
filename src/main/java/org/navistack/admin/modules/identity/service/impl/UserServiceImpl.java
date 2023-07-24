package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.RoleDao;
import org.navistack.admin.modules.identity.dao.UserDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.entity.UserRole;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.admin.modules.identity.service.UserService;
import org.navistack.admin.modules.identity.service.convert.UserConverter;
import org.navistack.admin.modules.identity.service.convert.UserDetailVmConverter;
import org.navistack.admin.modules.identity.service.convert.UserDtoConverter;
import org.navistack.admin.modules.identity.service.dto.UserDto;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;
import org.navistack.admin.support.mybatis.AuditingPropertiesSupport;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageBuilder;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        long totalRecords = dao.countByQuery(query);
        if (totalRecords <= 0) {
            return PageBuilder.emptyPage();
        }
        return dao.paginateByQuery(query, pageable)
                .stream()
                .map(UserConverter.INSTANCE::toDto)
                .collect(PageBuilder.collector(pageable, totalRecords));
    }

    @Override
    public UserDetailVm queryDetailById(Long id) {
        User user = Optional.ofNullable(dao.selectById(id))
                .orElseThrow(() -> new NoSuchEntityException("User does not exist"));
        UserDetailVm vm = UserDetailVmConverter.INSTANCE.fromEntity(user);
        List<Long> roleIds = userRoleDao.selectAllRoleIdsByUserId(id);
        vm.setRoleIds(roleIds);
        return vm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserDto dto) {
        Asserts.state(dto.getLoginName(), dto.getId(), this::validateAvailabilityOfLoginName, () -> new DomainValidationException("Login name has been taken already"));
        Asserts.state(dto.getMobileNumber(), dto.getId(), this::validateAvailabilityOfMobileNumber, () -> new DomainValidationException("Mobile number has been taken already"));
        Asserts.state(dto.getEmailAddress(), dto.getId(), this::validateAvailabilityOfEmailAddress, () -> new DomainValidationException("Email address has been taken already"));

        User entity = UserDtoConverter.INSTANCE.toEntity(dto);
        AuditingPropertiesSupport.created(entity);
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


        User entity = UserDtoConverter.INSTANCE.toEntity(dto);
        AuditingPropertiesSupport.updated(entity);
        dao.updateById(entity);
        replaceRolesOf(entity.getId(), dto.getRoleIds());
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById, () -> new NoSuchEntityException("User does not exist"));

        dao.deleteById(id);
        userRoleDao.deleteAllByUserId(id);
    }

    protected void replaceRolesOf(Long userId, List<Long> roleIds) {
        userRoleDao.deleteAllByUserId(userId);

        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }

        roleIds = roleDao.selectAllIdsByIds(roleIds);
        List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> UserRole.of(userId, roleId))
                .collect(Collectors.toList());
        userRoleDao.insertAll(userRoles);
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.existsById(id);
    }

    protected boolean validateAvailabilityOfLoginName(String loginName, Long modifiedId) {
        Long currentId = dao.selectIdByLoginName(loginName);
        return currentId == null || currentId.equals(modifiedId);
    }

    protected boolean validateAvailabilityOfMobileNumber(String mobileNumber, Long modifiedId) {
        Long currentId = dao.selectIdByMobileNumber(mobileNumber);
        return currentId == null || currentId.equals(modifiedId);
    }

    protected boolean validateAvailabilityOfEmailAddress(String emailAddress, Long modifiedId) {
        Long currentId = dao.selectIdByEmailAddress(emailAddress);
        return currentId == null || currentId.equals(modifiedId);
    }

    // endregion
}
