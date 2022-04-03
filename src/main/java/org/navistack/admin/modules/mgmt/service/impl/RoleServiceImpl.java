package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.dao.RoleDao;
import org.navistack.admin.modules.common.dao.RolePrivilegeDao;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.common.entity.RolePrivilege;
import org.navistack.admin.modules.mgmt.service.RoleService;
import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.dto.RoleQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
import org.navistack.framework.core.problem.DomainProblems;
import org.navistack.framework.core.utils.StaticModelMapper;
import org.navistack.framework.mybatisplusplus.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl
        extends AbstractCrudService<Long, Role, RoleDto, RoleQueryParams, RoleDao>
        implements RoleService {
    private final PrivilegeDao privilegeDao;
    private final RolePrivilegeDao rolePrivilegeDao;

    public RoleServiceImpl(RoleDao roleDao, PrivilegeDao privilegeDao, RolePrivilegeDao rolePrivilegeDao) {
        super(roleDao);
        this.privilegeDao = privilegeDao;
        this.rolePrivilegeDao = rolePrivilegeDao;
    }

    @Override
    protected Wrapper<Role> buildWrapper(RoleQueryParams queryParams) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();

        return Wrappers.<Role>lambdaQuery()
                .eq(id != null, Role::getId, id)
                .eq(code != null, Role::getCode, code)
                .like(name != null, Role::getName, name);
    }

    @Override
    public RoleDetailVm queryDetailById(Long roleId) {
        Role role = dao.selectById(roleId);

        RoleDetailVm vm = StaticModelMapper.map(role, RoleDetailVm.class);

        List<Long> privilegeIds = rolePrivilegeDao.selectList(
                        Wrappers.<RolePrivilege>lambdaQuery()
                                .eq(RolePrivilege::getRoleId, roleId)
                )
                .stream()
                .map(RolePrivilege::getPrivilegeId)
                .collect(Collectors.toList());

        vm.setPrivilegeIds(privilegeIds);

        return vm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RoleDto dto) {
        dto.setId(null);

        boolean existing = dao.exists(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getCode, dto.getCode())
        );

        if (existing) {
            throw DomainProblems.entityDuplicated("Role existed");
        }

        Role role = StaticModelMapper.map(dto, Role.class);

        dao.insert(role);

        replacePrivilegesOf(role.getId(), dto.getPrivilegeIds());

        StaticModelMapper.map(role, dto);
    }

    @Override
    protected void preModify(RoleDto dto) {
        super.preModify(dto);

        boolean existing = dao.exists(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getCode, dto.getCode())
                        .ne(Role::getId, dto.getId())
        );

        if (existing) {
            throw DomainProblems.entityDuplicated("Role existed");
        }
    }

    protected void replacePrivilegesOf(Long roleId, List<Long> privilegeIds) {
        rolePrivilegeDao.delete(
                Wrappers.<RolePrivilege>lambdaQuery()
                        .eq(RolePrivilege::getRoleId, roleId)
        );

        Optional.ofNullable(privilegeIds).orElse(Collections.emptyList())
                .stream()
                .filter(privilegeId -> privilegeDao.selectCount(
                                Wrappers.<Privilege>lambdaQuery()
                                        .eq(Privilege::getId, privilegeId)
                        ) > 0
                )
                .map(privilegeId -> RolePrivilege.of(roleId, privilegeId))
                .forEach(rolePrivilegeDao::insert);
    }
}
