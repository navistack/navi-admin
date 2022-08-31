package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.DictDao;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.mgmt.service.DictService;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.admin.modules.mgmt.service.dto.DictQueryDto;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.mybatisplusplus.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl
        extends AbstractCrudService<Dict, Long, DictDto, DictQueryDto, DictDao>
        implements DictService {
    public DictServiceImpl(DictDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<Dict> buildWrapper(DictQueryDto queryDto) {
        Long id = queryDto.getId();
        String code = queryDto.getCode();
        String name = queryDto.getName();

        return Wrappers.<Dict>lambdaQuery()
                .eq(id != null, Dict::getId, id)
                .eq(code != null, Dict::getCode, code)
                .like(name != null, Dict::getName, name);
    }

    @Override
    protected void preCreate(DictDto dto) {
        super.preCreate(dto);

        dto.setId(null);
        boolean existing = dao.exists(
                Wrappers.<Dict>lambdaQuery()
                        .eq(Dict::getCode, dto.getCode())
        );

        if (existing) {
            throw new EntityDuplicationException("Dict existed");
        }
    }

    @Override
    protected void preModify(DictDto dto) {
        super.preModify(dto);

        boolean existing = dao.exists(
                Wrappers.<Dict>lambdaQuery()
                        .eq(Dict::getCode, dto.getCode())
                        .ne(Dict::getId, dto.getId())
        );

        if (existing) {
            throw new EntityDuplicationException("Dict existed");
        }
    }
}
