package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.OrgDao;
import org.navistack.admin.modules.common.entity.Org;
import org.navistack.admin.modules.mgmt.service.OrgService;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;
import org.navistack.admin.modules.mgmt.service.dto.OrgQueryParams;
import org.navistack.framework.crudsupport.AbstractCrudService;
import org.navistack.framework.crudsupport.problems.DuplicatedEntityProblem;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl
        extends AbstractCrudService<Long, Org, OrgDto, OrgQueryParams, OrgDao>
        implements OrgService {

    public OrgServiceImpl(OrgDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<Org> buildWrapper(OrgQueryParams queryParams) {
        String code = queryParams.getCode();
        String name = queryParams.getName();
        Long superId = queryParams.getSuperId();

        return Wrappers.<Org>lambdaQuery()
                .eq(code != null, Org::getCode, code)
                .eq(name != null, Org::getName, name)
                .eq(superId != null, Org::getSuperId, superId);
    }

    @Override
    protected void preCreate(OrgDto dto) {
        super.preCreate(dto);

        dto.setId(null);

        Long cnt = dao.selectCount(
                Wrappers.<Org>lambdaQuery()
                        .eq(Org::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Organization existed");
        }
    }

    @Override
    protected void preModify(OrgDto dto) {
        super.preModify(dto);

        Long cnt = dao.selectCount(
                Wrappers.<Org>lambdaQuery()
                        .eq(Org::getCode, dto.getCode())
                        .ne(Org::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Organization existed");
        }
    }
}
