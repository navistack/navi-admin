package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.DictDao;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.mgmt.service.DictService;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.admin.modules.mgmt.service.dto.DictQueryParams;
import org.navistack.framework.crudsupport.AbstractCrudService;
import org.navistack.framework.crudsupport.problems.DuplicatedEntityProblem;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl
        extends AbstractCrudService<Long, Dict, DictDto, DictQueryParams, DictDao>
        implements DictService {
    public DictServiceImpl(DictDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<Dict> buildWrapper(DictQueryParams queryParams) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();

        return Wrappers.<Dict>lambdaQuery()
                .eq(id != null, Dict::getId, id)
                .eq(code != null, Dict::getCode, code)
                .like(name != null, Dict::getName, name);
    }

    @Override
    protected void preCreate(DictDto dto) {
        super.preCreate(dto);

        dto.setId(null);
        Long cnt = dao.selectCount(
                Wrappers.<Dict>lambdaQuery()
                        .eq(Dict::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Dict existed");
        }
    }

    @Override
    protected void preModify(DictDto dto) {
        super.preModify(dto);

        Long cnt = dao.selectCount(
                Wrappers.<Dict>lambdaQuery()
                        .eq(Dict::getCode, dto.getCode())
                        .ne(Dict::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new DuplicatedEntityProblem("Dict existed");
        }
    }
}
