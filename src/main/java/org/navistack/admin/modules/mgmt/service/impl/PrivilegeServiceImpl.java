package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.mgmt.service.PrivilegeService;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeQueryParams;
import org.navistack.framework.core.problem.DomainProblems;
import org.navistack.framework.mybatisplusplus.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl
        extends AbstractCrudService<Long, Privilege, PrivilegeDto, PrivilegeQueryParams, PrivilegeDao>
        implements PrivilegeService {

    public PrivilegeServiceImpl(PrivilegeDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<Privilege> buildWrapper(PrivilegeQueryParams queryParams) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();
        Long parentId = queryParams.getParentId();

        return Wrappers.<Privilege>lambdaQuery()
                .eq(id != null, Privilege::getId, id)
                .eq(code != null, Privilege::getCode, code)
                .like(name != null, Privilege::getName, name)
                .eq(parentId != null, Privilege::getParentId, parentId);
    }

    @Override
    protected void preCreate(PrivilegeDto dto) {
        super.preCreate(dto);

        dto.setId(null);

        boolean existing = dao.exists(
                Wrappers.<Privilege>lambdaQuery()
                        .eq(Privilege::getCode, dto.getCode())
        );

        if (existing) {
            throw DomainProblems.entityDuplicated("Privilege existed");
        }
    }

    @Override
    protected void preModify(PrivilegeDto dto) {
        super.preModify(dto);

        boolean existing = dao.exists(
                Wrappers.<Privilege>lambdaQuery()
                        .eq(Privilege::getCode, dto.getCode())
                        .ne(Privilege::getId, dto.getId())
        );

        if (existing) {
            throw DomainProblems.entityDuplicated("Privilege existed");
        }
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }
}
