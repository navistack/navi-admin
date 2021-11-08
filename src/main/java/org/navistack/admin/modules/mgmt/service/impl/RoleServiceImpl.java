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
import org.navistack.admin.support.problems.EntityDuplicatedProblem;
import org.navistack.admin.utils.MyBatisPlusUtils;
import org.navistack.framework.core.utils.StaticModelMapper;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;
    private final PrivilegeDao privilegeDao;
    private final RolePrivilegeDao rolePrivilegeDao;

    public RoleServiceImpl(RoleDao roleDao, PrivilegeDao privilegeDao, RolePrivilegeDao rolePrivilegeDao) {
        this.roleDao = roleDao;
        this.privilegeDao = privilegeDao;
        this.rolePrivilegeDao = rolePrivilegeDao;
    }

    @Override
    public Page<Role> paginate(RoleQueryParams queryParams, Pageable pageable) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();

        Wrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .eq(id != null, Role::getId, id)
                .eq(code != null, Role::getCode, code)
                .like(name != null, Role::getName, name);

        return MyBatisPlusUtils.PageUtils.toPage(
                roleDao.selectPage(
                        MyBatisPlusUtils.PageUtils.fromPageable(pageable),
                        wrapper
                )
        );
    }

    @Override
    public RoleDetailVm queryDetailById(Long roleId) {
        Role role = roleDao.selectById(roleId);

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

        Long cnt = roleDao.selectCount(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Role existed");
        }

        Role role = StaticModelMapper.map(dto, Role.class);

        roleDao.insert(role);

        replacePrivilegesOf(role.getId(), dto.getPrivilegeIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(RoleDto dto) {
        Long cnt = roleDao.selectCount(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getCode, dto.getCode())
                        .ne(Role::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Role existed");
        }

        Role role = StaticModelMapper.map(dto, Role.class);

        roleDao.updateById(role);
    }

    @Override
    public void remove(Long id) {
        roleDao.deleteById(id);
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
