package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.OrgDao;
import org.navistack.admin.modules.common.entity.Org;
import org.navistack.admin.modules.mgmt.service.OrgService;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;
import org.navistack.admin.modules.mgmt.service.dto.OrgQueryDto;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.mybatisplusplus.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl
        extends AbstractCrudService<Org, Long, OrgDto, OrgQueryDto, OrgDao>
        implements OrgService {

    public OrgServiceImpl(OrgDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<Org> buildWrapper(OrgQueryDto queryDto) {
        String code = queryDto.getCode();
        String name = queryDto.getName();
        Long superId = queryDto.getSuperId();

        return Wrappers.<Org>lambdaQuery()
                .eq(code != null, Org::getCode, code)
                .eq(name != null, Org::getName, name)
                .eq(superId != null, Org::getSuperId, superId);
    }

    @Override
    protected void preCreate(OrgDto dto) {
        super.preCreate(dto);

        dto.setId(null);

        boolean existing = dao.exists(
                Wrappers.<Org>lambdaQuery()
                        .eq(Org::getCode, dto.getCode())
        );

        if (existing) {
            throw new EntityDuplicationException("Organization existed");
        }
    }

    @Override
    protected void preModify(OrgDto dto) {
        super.preModify(dto);

        boolean existing = dao.exists(
                Wrappers.<Org>lambdaQuery()
                        .eq(Org::getCode, dto.getCode())
                        .ne(Org::getId, dto.getId())
        );

        if (existing) {
            throw new EntityDuplicationException("Organization existed");
        }
    }
}
