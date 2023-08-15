package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.dao.RoleDao;
import org.navistack.admin.modules.identity.dao.RolePrivilegeDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.dtobj.RoleDo;
import org.navistack.admin.modules.identity.dtobj.RolePrivilegeDo;
import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.admin.modules.identity.service.RoleService;
import org.navistack.admin.modules.identity.service.convert.RoleDetailVmConvert;
import org.navistack.admin.modules.identity.service.convert.RoleDoConvert;
import org.navistack.admin.modules.identity.service.convert.RoleVmConvert;
import org.navistack.admin.modules.identity.service.dto.RoleCreateDto;
import org.navistack.admin.modules.identity.service.dto.RoleModifyDto;
import org.navistack.admin.modules.identity.service.vm.RoleDetailVm;
import org.navistack.admin.modules.identity.service.vm.RoleVm;
import org.navistack.admin.support.mybatis.AuditingPropertiesSupport;
import org.navistack.framework.core.error.ConstraintViolationException;
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
public class RoleServiceImpl implements RoleService {
    private final RoleDao dao;
    private final PrivilegeDao privilegeDao;
    private final RolePrivilegeDao rolePrivilegeDao;
    private final UserRoleDao userRoleDao;

    public RoleServiceImpl(
            RoleDao roleDao,
            PrivilegeDao privilegeDao,
            RolePrivilegeDao rolePrivilegeDao,
            UserRoleDao userRoleDao) {
        this.dao = roleDao;
        this.privilegeDao = privilegeDao;
        this.rolePrivilegeDao = rolePrivilegeDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public Page<RoleVm> paginate(RoleQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        if (totalRecords <= 0) {
            return PageBuilder.emptyPage();
        }
        return dao.paginateByQuery(query, pageable)
                .stream()
                .map(RoleVmConvert.INSTANCE::from)
                .collect(PageBuilder.collector(pageable, totalRecords));
    }

    @Override
    public RoleDetailVm queryDetailById(Long id) {
        RoleDo role = Optional.ofNullable(dao.selectById(id))
                .orElseThrow(() -> new NoSuchEntityException("Role does not exist"));
        List<Long> privilegeIds = rolePrivilegeDao.selectAllPrivilegeIdsByRoleId(id);
        RoleDetailVm vm = RoleDetailVmConvert.INSTANCE.from(role);
        vm.setPrivilegeIds(privilegeIds);
        return vm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RoleCreateDto dto) {
        Asserts.state(dto.getCode(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Role code has been taken already"));

        RoleDo dtObj = RoleDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.created(dtObj);
        dao.insert(dtObj);
        replacePrivilegesOf(dtObj.getId(), dto.getPrivilegeIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(RoleModifyDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Role does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Role code has been taken already"));

        RoleDo dtObj = RoleDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.updated(dtObj);
        dao.updateById(dtObj);
        replacePrivilegesOf(dtObj.getId(), dto.getPrivilegeIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById, () -> new NoSuchEntityException("Role does not exist"));
        Asserts.state(id, this::validateAbsenceOfUser, () -> new ConstraintViolationException("Role can not be removed due to user(s) attached"));

        dao.deleteById(id);
        rolePrivilegeDao.deleteAllByRoleId(id);
    }

    protected void replacePrivilegesOf(Long roleId, List<Long> privilegeIds) {
        rolePrivilegeDao.deleteAllByRoleId(roleId);

        if (privilegeIds == null || privilegeIds.isEmpty()) {
            return;
        }

        privilegeIds = privilegeDao.selectAllIdsByIds(privilegeIds);
        List<RolePrivilegeDo> rolePrivileges = privilegeIds.stream()
                .map(privilegeId -> RolePrivilegeDo.of(roleId, privilegeId))
                .collect(Collectors.toList());
        rolePrivilegeDao.insertAll(rolePrivileges);
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.existsById(id);
    }

    protected boolean validateAvailabilityOfCode(String code) {
        Long currentId = dao.selectIdByCode(code);
        return currentId == null;
    }

    protected boolean validateAvailabilityOfCode(String code, Long modifiedId) {
        Long currentId = dao.selectIdByCode(code);
        return currentId == null || currentId.equals(modifiedId);
    }

    protected boolean validateAbsenceOfUser(Long roleId) {
        return roleId == null || !userRoleDao.existsByRoleId(roleId);
    }

    // endregion
}
