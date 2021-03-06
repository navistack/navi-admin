package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.DictItemDao;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.mgmt.service.DictItemService;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.admin.modules.mgmt.service.dto.DictItemQueryParams;
import org.navistack.framework.core.problem.DomainProblems;
import org.navistack.framework.mybatisplusplus.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class DictItemServiceImpl
        extends AbstractCrudService<DictItem, Long, DictItemDto, DictItemQueryParams, DictItemDao>
        implements DictItemService {

    public DictItemServiceImpl(DictItemDao dao) {
        super(dao);
    }

    @Override
    protected Wrapper<DictItem> buildWrapper(DictItemQueryParams queryParams) {
        Long id = queryParams.getId();
        String name = queryParams.getName();
        String itKey = queryParams.getItKey();
        String dictCode = queryParams.getDictCode();

        return Wrappers.<DictItem>lambdaQuery()
                .eq(id != null, DictItem::getId, id)
                .like(name != null, DictItem::getName, name)
                .eq(itKey != null, DictItem::getItKey, itKey)
                .eq(dictCode != null, DictItem::getDictCode, dictCode);
    }

    @Override
    protected void preCreate(DictItemDto dto) {
        super.preCreate(dto);

        dto.setId(null);

        boolean existing = dao.exists(
                Wrappers.<DictItem>lambdaQuery()
                        .eq(DictItem::getDictCode, dto.getDictCode())
        );

        if (existing) {
            throw DomainProblems.entityDuplicated("Item existed");
        }
    }

    @Override
    protected void preModify(DictItemDto dto) {
        super.preModify(dto);

        boolean existing = dao.exists(
                Wrappers.<DictItem>lambdaQuery()
                        .eq(DictItem::getDictCode, dto.getDictCode())
                        .ne(DictItem::getId, dto.getId())
        );

        if (existing) {
            throw DomainProblems.entityDuplicated("Item existed");
        }
    }
}
