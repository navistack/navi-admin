package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.GeoDivisionDao;
import org.navistack.admin.modules.common.entity.GeoDivision;
import org.navistack.admin.modules.mgmt.service.GeoDivisionService;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionDto;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionQueryParams;
import org.navistack.framework.crudsupport.AbstractCrudService;
import org.navistack.framework.crudsupport.problems.DuplicatedEntityProblem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeoDivisionServiceImpl
        extends AbstractCrudService<Long, GeoDivision, GeoDivisionDto, GeoDivisionQueryParams, GeoDivisionDao>
        implements GeoDivisionService {

    public GeoDivisionServiceImpl(GeoDivisionDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<GeoDivision> buildWrapper(GeoDivisionQueryParams queryParams) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();
        String parentCode = queryParams.getParentCode();

        return Wrappers.<GeoDivision>lambdaQuery()
                .eq(id != null, GeoDivision::getId, id)
                .eq(code != null, GeoDivision::getCode, code)
                .like(name != null, GeoDivision::getName, name)
                .eq(parentCode != null, GeoDivision::getParentCode, parentCode);
    }

    @Override
    protected void preCreate(GeoDivisionDto dto) {
        super.preCreate(dto);

        dto.setId(null);

        Long cnt = dao.selectCount(
                Wrappers.<GeoDivision>lambdaQuery()
                        .eq(GeoDivision::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Item existed");
        }
    }

    @Override
    @Transactional
    protected void preModify(GeoDivisionDto dto) {
        super.preModify(dto);

        Long cnt = dao.selectCount(
                Wrappers.<GeoDivision>lambdaQuery()
                        .eq(GeoDivision::getCode, dto.getCode())
                        .ne(GeoDivision::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Item existed");
        }
    }
}
