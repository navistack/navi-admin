package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.dao.RoleDao;
import org.navistack.admin.modules.common.dao.RolePrivilegeDao;
import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.common.entity.RolePrivilege;
import org.navistack.admin.modules.common.query.PrivilegeQuery;
import org.navistack.admin.modules.common.query.RolePrivilegeQuery;
import org.navistack.admin.modules.common.query.RoleQuery;
import org.navistack.admin.modules.mgmt.service.RoleService;
import org.navistack.admin.modules.mgmt.service.convert.RoleConverter;
import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
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
public class RoleServiceImpl implements RoleService {
    private final RoleDao dao;
    private final PrivilegeDao privilegeDao;
    private final RolePrivilegeDao rolePrivilegeDao;

    public RoleServiceImpl(
            RoleDao roleDao,
            PrivilegeDao privilegeDao,
            RolePrivilegeDao rolePrivilegeDao) {
        this.dao = roleDao;
        this.privilegeDao = privilegeDao;
        this.rolePrivilegeDao = rolePrivilegeDao;
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
        ensureUnique(dto);

        Role entity = RoleConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
        replacePrivilegesOf(entity.getId(), dto.getPrivilegeIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(RoleDto dto) {
        ensureUnique(dto);

        Role entity = RoleConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
        replacePrivilegesOf(entity.getId(), dto.getPrivilegeIds());
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }

    protected void ensureUnique(RoleDto dto) {
        RoleQuery queryDto = new RoleQuery();
        queryDto.setCode(dto.getCode());
        Role existedOne = dao.selectOne(queryDto);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Role has existed");
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
}
