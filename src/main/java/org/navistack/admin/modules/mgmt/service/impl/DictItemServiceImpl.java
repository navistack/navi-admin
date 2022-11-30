package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.DictItemDao;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.common.query.DictItemQuery;
import org.navistack.admin.modules.mgmt.service.DictItemService;
import org.navistack.admin.modules.mgmt.service.convert.DictItemConverter;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DictItemServiceImpl implements DictItemService {
    private final DictItemDao dao;

    public DictItemServiceImpl(DictItemDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<DictItemDto> paginate(DictItemQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<DictItem> entities = dao.selectWithPageable(query, pageable);
        Collection<DictItemDto> dtos = DictItemConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(DictItemDto dto) {
        ensureUnique(dto);

        DictItem entity = DictItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(DictItemDto dto) {
        ensureUnique(dto);

        DictItem entity = DictItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }

    protected void ensureUnique(DictItemDto dto) {
        DictItemQuery query = DictItemQuery.builder()
                .itKey(dto.getItKey())
                .build();
        DictItem existedOne = dao.selectOne(query);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Dict item already exists");
    }
}
