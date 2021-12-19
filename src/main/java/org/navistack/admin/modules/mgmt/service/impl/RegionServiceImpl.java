package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.mgmt.service.RegionService;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;
import org.navistack.admin.modules.mgmt.service.dto.RegionQueryParams;
import org.navistack.framework.crudsupport.AbstractCrudService;
import org.navistack.framework.crudsupport.problems.DuplicatedEntityProblem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegionServiceImpl
        extends AbstractCrudService<Long, Region, RegionDto, RegionQueryParams, RegionDao>
        implements RegionService {

    public RegionServiceImpl(RegionDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<Region> buildWrapper(RegionQueryParams queryParams) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();
        String parentCode = queryParams.getParentCode();

        return Wrappers.<Region>lambdaQuery()
                .eq(id != null, Region::getId, id)
                .eq(code != null, Region::getCode, code)
                .like(name != null, Region::getName, name)
                .eq(parentCode != null, Region::getParentCode, parentCode);
    }

    @Override
    protected void preCreate(RegionDto dto) {
        super.preCreate(dto);

        dto.setId(null);

        Long cnt = dao.selectCount(
                Wrappers.<Region>lambdaQuery()
                        .eq(Region::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Item existed");
        }
    }

    @Override
    @Transactional
    protected void preModify(RegionDto dto) {
        super.preModify(dto);

        Long cnt = dao.selectCount(
                Wrappers.<Region>lambdaQuery()
                        .eq(Region::getCode, dto.getCode())
                        .ne(Region::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Item existed");
        }
    }
}
