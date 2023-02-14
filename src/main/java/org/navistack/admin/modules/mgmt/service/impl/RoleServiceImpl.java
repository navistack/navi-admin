package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.dao.RoleDao;
import org.navistack.admin.modules.common.dao.RolePrivilegeDao;
import org.navistack.admin.modules.common.dao.UserRoleDao;
import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.common.entity.RolePrivilege;
import org.navistack.admin.modules.common.query.PrivilegeQuery;
import org.navistack.admin.modules.common.query.RolePrivilegeQuery;
import org.navistack.admin.modules.common.query.RoleQuery;
import org.navistack.admin.modules.common.query.UserRoleQuery;
import org.navistack.admin.modules.mgmt.service.RoleService;
import org.navistack.admin.modules.mgmt.service.convert.RoleConverter;
import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
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
        long totalRecords = dao.count(query);
        List<Role> entities = dao.selectWithPageable(query, pageable);
        Collection<RoleDto> dtos = RoleConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public RoleDetailVm queryDetailById(Long id) {
        Role role = dao.selectOneById(id);

        RoleDetailVm vm = ModelMappers.map(role, RoleDetailVm.class);

        RolePrivilegeQuery rpq = new RolePrivilegeQuery();
        rpq.setRoleId(id);
        List<Long> privilegeIds = rolePrivilegeDao.select(rpq)
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

        Role entity = RoleConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
        replacePrivilegesOf(entity.getId(), dto.getPrivilegeIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(RoleDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Role does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Role code has been taken already"));

        Role entity = RoleConverter.INSTANCE.dtoToEntity(dto);
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

        RolePrivilegeQuery rpq = RolePrivilegeQuery.builder()
                .roleId(id)
                .build();
        rolePrivilegeDao.delete(rpq);
    }

    protected void replacePrivilegesOf(Long roleId, List<Long> privilegeIds) {
        RolePrivilegeQuery rpq = RolePrivilegeQuery.builder()
                .roleId(roleId)
                .build();
        rolePrivilegeDao.delete(rpq);

        if (privilegeIds == null || privilegeIds.isEmpty()) {
            return;
        }

        for (Long privilegeId : privilegeIds) {
            PrivilegeQuery rq = PrivilegeQuery.builder()
                    .id(privilegeId)
                    .build();
            if (privilegeDao.count(rq) > 0) {
                RolePrivilege entity = RolePrivilege.of(roleId, privilegeId);
                rolePrivilegeDao.insert(entity);
            }
        }
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.selectOneById(id) != null;
    }

    protected boolean validateAvailabilityOfCode(String code, Long modifiedId) {
        RoleQuery query = RoleQuery.builder()
                .code(code)
                .build();
        Role existingOne = dao.selectOne(query);

        if (existingOne == null) {
            return true;
        }

        if (!existingOne.getCode().equals(code)) {
            return true;
        }

        if (existingOne.getId().equals(modifiedId)) {
            return true;
        }

        return false;
    }

    protected boolean validateAbsenceOfUser(Long roleId) {
        UserRoleQuery query = UserRoleQuery.builder()
                .roleId(roleId)
                .build();
        return userRoleDao.count(query) <= 0;
    }

    // endregion
}
