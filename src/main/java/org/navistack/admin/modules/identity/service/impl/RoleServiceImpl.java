package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.dao.RoleDao;
import org.navistack.admin.modules.identity.dao.RolePrivilegeDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.entity.Role;
import org.navistack.admin.modules.identity.entity.RolePrivilege;
import org.navistack.admin.modules.identity.query.RolePrivilegeQuery;
import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.admin.modules.identity.service.RoleService;
import org.navistack.admin.modules.identity.service.convert.RoleConverter;
import org.navistack.admin.modules.identity.service.convert.RoleDtoConverter;
import org.navistack.admin.modules.identity.service.dto.RoleDto;
import org.navistack.admin.modules.identity.service.vm.RoleDetailVm;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.ConstraintViolationException;
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
    public Page<RoleDto> paginate(RoleQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        List<Role> entities = dao.paginateByQuery(query, pageable);
        Collection<RoleDto> dtos = RoleConverter.INSTANCE.toDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public RoleDetailVm queryDetailById(Long id) {
        Role role = dao.selectById(id);

        RoleDetailVm vm = ModelMappers.map(role, RoleDetailVm.class);

        RolePrivilegeQuery rpq = new RolePrivilegeQuery();
        rpq.setRoleId(id);
        List<Long> privilegeIds = rolePrivilegeDao.selectAllByQuery(rpq)
                .stream()
                .map(RolePrivilege::getPrivilegeId)
                .collect(Collectors.toList());

        vm.setPrivilegeIds(privilegeIds);

        return vm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RoleDto dto) {
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Role code has been taken already"));

        Role entity = RoleDtoConverter.INSTANCE.toEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
        replacePrivilegesOf(entity.getId(), dto.getPrivilegeIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(RoleDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Role does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Role code has been taken already"));

        Role entity = RoleDtoConverter.INSTANCE.toEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
        replacePrivilegesOf(entity.getId(), dto.getPrivilegeIds());
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
        List<RolePrivilege> rolePrivileges = privilegeIds.stream()
                .map(privilegeId -> RolePrivilege.of(roleId, privilegeId))
                .toList();
        rolePrivilegeDao.insertAll(rolePrivileges);
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.existsById(id);
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
