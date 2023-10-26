package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.RoleDao;
import org.navistack.admin.modules.identity.dao.UserDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.identity.dtobj.UserRoleDo;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.admin.modules.identity.service.UserService;
import org.navistack.admin.modules.identity.service.convert.UserDetailVmConvert;
import org.navistack.admin.modules.identity.service.convert.UserDoConvert;
import org.navistack.admin.modules.identity.service.convert.UserVmConvert;
import org.navistack.admin.modules.identity.service.dto.UserCreateDto;
import org.navistack.admin.modules.identity.service.dto.UserModifyDto;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;
import org.navistack.admin.modules.identity.service.vm.UserVm;
import org.navistack.admin.support.mybatis.AuditingPropertiesSupport;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageBuilder;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           RoleDao roleDao,
                           UserRoleDao userRoleDao,
                           PasswordEncoder passwordEncoder) {
        this.dao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    public UserServiceImpl(UserDao userDao,
                           RoleDao roleDao,
                           UserRoleDao userRoleDao) {
        this(
                userDao,
                roleDao,
                userRoleDao,
                PasswordEncoderFactories.createDelegatingPasswordEncoder()
        );
    }

    @Override
    public Page<UserVm> paginate(UserQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        if (totalRecords <= 0) {
            return PageBuilder.emptyPage();
        }
        return dao.paginateByQuery(query, pageable)
                .stream()
                .map(UserVmConvert.INSTANCE::from)
                .collect(PageBuilder.collector(pageable, totalRecords));
    }

    @Override
    public UserDetailVm queryDetailById(Long id) {
        UserDo user = Optional.ofNullable(dao.selectById(id))
                .orElseThrow(() -> new NoSuchEntityException("User does not exist"));
        UserDetailVm vm = UserDetailVmConvert.INSTANCE.from(user);
        List<Long> roleIds = userRoleDao.selectAllRoleIdsByUserId(id);
        vm.setRoleIds(roleIds);
        return vm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserCreateDto dto) {
        Asserts.state(dto.getLoginName(), this::validateAvailabilityOfLoginName,
                () -> new DomainValidationException("Login name has been taken already"));
        Asserts.state(dto.getMobileNumber(), this::validateAvailabilityOfMobileNumber,
                () -> new DomainValidationException("Mobile number has been taken already"));
        Asserts.state(dto.getEmailAddress(), this::validateAvailabilityOfEmailAddress,
                () -> new DomainValidationException("Email address has been taken already"));

        String plainPassword = dto.getPassword();
        String password = passwordEncoder.encode(plainPassword);

        UserDo dtObj = UserDoConvert.INSTANCE.from(dto);
        dtObj.setPassword(password);
        AuditingPropertiesSupport.created(dtObj);
        dao.insert(dtObj);
        replaceRolesOf(dtObj.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(UserModifyDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById,
                () -> new NoSuchEntityException("User does not exist"));
        Asserts.state(dto.getLoginName(), dto.getId(), this::validateAvailabilityOfLoginName,
                () -> new DomainValidationException("Login name has been taken already"));
        Asserts.state(dto.getMobileNumber(), dto.getId(), this::validateAvailabilityOfMobileNumber,
                () -> new DomainValidationException("Mobile number has been taken already"));
        Asserts.state(dto.getEmailAddress(), dto.getId(), this::validateAvailabilityOfEmailAddress,
                () -> new DomainValidationException("Email address has been taken already"));

        String plainPassword = dto.getPassword();
        String password = plainPassword != null
                ? passwordEncoder.encode(plainPassword)
                : null;

        UserDo dtObj = UserDoConvert.INSTANCE.from(dto);
        dtObj.setPassword(password);
        AuditingPropertiesSupport.updated(dtObj);
        dao.updateById(dtObj);
        replaceRolesOf(dtObj.getId(), dto.getRoleIds());
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById,
                () -> new NoSuchEntityException("User does not exist"));

        dao.deleteById(id);
        userRoleDao.deleteAllByUserId(id);
    }

    protected void replaceRolesOf(Long userId, List<Long> roleIds) {
        userRoleDao.deleteAllByUserId(userId);

        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }

        roleIds = roleDao.selectAllIdsByIds(roleIds);
        List<UserRoleDo> userRoles = roleIds.stream()
                .map(roleId -> UserRoleDo.of(userId, roleId))
                .collect(Collectors.toList());
        userRoleDao.insertAll(userRoles);
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.existsById(id);
    }

    protected boolean validateAvailabilityOfLoginName(String loginName) {
        Long currentId = dao.selectIdByLoginName(loginName);
        return currentId == null;
    }

    protected boolean validateAvailabilityOfLoginName(String loginName, Long modifiedId) {
        Long currentId = dao.selectIdByLoginName(loginName);
        return currentId == null || currentId.equals(modifiedId);
    }

    protected boolean validateAvailabilityOfMobileNumber(String mobileNumber) {
        Long currentId = dao.selectIdByMobileNumber(mobileNumber);
        return currentId == null;
    }

    protected boolean validateAvailabilityOfMobileNumber(String mobileNumber, Long modifiedId) {
        Long currentId = dao.selectIdByMobileNumber(mobileNumber);
        return currentId == null || currentId.equals(modifiedId);
    }

    protected boolean validateAvailabilityOfEmailAddress(String emailAddress) {
        Long currentId = dao.selectIdByEmailAddress(emailAddress);
        return currentId == null;
    }

    protected boolean validateAvailabilityOfEmailAddress(String emailAddress, Long modifiedId) {
        Long currentId = dao.selectIdByEmailAddress(emailAddress);
        return currentId == null || currentId.equals(modifiedId);
    }

    // endregion
}
