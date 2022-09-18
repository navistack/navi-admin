package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.mgmt.service.PrivilegeService;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeQueryDto;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.mybatisplusplus.AbstractCrudService;
import org.navistack.framework.mybatisplusplus.utils.Wrappers;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl
        extends AbstractCrudService<Privilege, Long, PrivilegeDto, PrivilegeQueryDto, PrivilegeDao>
        implements PrivilegeService {

    public PrivilegeServiceImpl(PrivilegeDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<Privilege> buildWrapper(PrivilegeQueryDto queryDto) {
        Long id = queryDto.getId();
        String code = queryDto.getCode();
        String name = queryDto.getName();
        Long parentId = queryDto.getParentId();

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
            throw new EntityDuplicationException("Privilege existed");
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
            throw new EntityDuplicationException("Privilege existed");
        }
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }
}
