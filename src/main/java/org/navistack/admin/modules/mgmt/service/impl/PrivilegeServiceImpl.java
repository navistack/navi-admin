package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.mgmt.service.PrivilegeService;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeQueryParams;
import org.navistack.admin.support.problems.EntityDuplicatedProblem;
import org.navistack.admin.utils.MyBatisPlusUtils;
import org.navistack.framework.core.utils.StaticModelMapper;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeDao dao;

    public PrivilegeServiceImpl(PrivilegeDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<Privilege> paginate(PrivilegeQueryParams queryParams, Pageable pageable) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();
        Long parentId = queryParams.getParentId();

        Wrapper<Privilege> wrapper = Wrappers.<Privilege>lambdaQuery()
                .eq(id != null, Privilege::getId, id)
                .eq(code != null, Privilege::getCode, code)
                .like(name != null, Privilege::getName, name)
                .eq(parentId != null, Privilege::getParentId, parentId);

        return MyBatisPlusUtils.PageUtils.toPage(
                dao.selectPage(
                        MyBatisPlusUtils.PageUtils.fromPageable(pageable),
                        wrapper
                )
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PrivilegeDto dto) {
        dto.setId(null);

        Long cnt = dao.selectCount(
                Wrappers.<Privilege>lambdaQuery()
                        .eq(Privilege::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Privilege existed");
        }

        Privilege privilege = StaticModelMapper.map(dto, Privilege.class);

        dao.insert(privilege);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(PrivilegeDto dto) {
        Long cnt = dao.selectCount(
                Wrappers.<Privilege>lambdaQuery()
                        .eq(Privilege::getCode, dto.getCode())
                        .ne(Privilege::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Privilege existed");
        }

        Privilege privilege = StaticModelMapper.map(dto, Privilege.class);

        dao.updateById(privilege);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }
}
