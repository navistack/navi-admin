package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.DictionaryDao;
import org.navistack.admin.modules.common.dao.DictionaryItemDao;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.mgmt.service.DictionaryService;
import org.navistack.admin.modules.mgmt.service.convert.DictionaryConverter;
import org.navistack.admin.modules.mgmt.service.convert.DictionaryItemConverter;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryDto;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryItemDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryDao dao;

    private final DictionaryItemDao itemDao;

    public DictionaryServiceImpl(
            DictionaryDao dao,
            DictionaryItemDao itemDao
    ) {
        this.dao = dao;
        this.itemDao = itemDao;
    }

    @Override
    public Page<DictionaryDto> paginate(DictionaryQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<Dictionary> entities = dao.selectWithPageable(query, pageable);
        Collection<DictionaryDto> dtos = DictionaryConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(DictionaryDto dto) {
        ensureUnique(dto);

        Dictionary entity = DictionaryConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(DictionaryDto dto) {
        ensureUnique(dto);

        Dictionary entity = DictionaryConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }



    @Override
    public Page<DictionaryItemDto> paginateItem(DictionaryItemQuery query, Pageable pageable) {
        long totalRecords = itemDao.count(query);
        List<DictionaryItem> entities = itemDao.selectWithPageable(query, pageable);
        Collection<DictionaryItemDto> dtos = DictionaryItemConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void createItem(DictionaryItemDto dto) {
        ensureItemUnique(dto);

        DictionaryItem entity = DictionaryItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        itemDao.insert(entity);
    }

    @Override
    public void modifyItem(DictionaryItemDto dto) {
        ensureItemUnique(dto);

        DictionaryItem entity = DictionaryItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        itemDao.updateById(entity);
    }

    @Override
    public void removeItem(Long id) {
        itemDao.deleteById(id);
    }

    protected void ensureUnique(DictionaryDto dto) {
        DictionaryQuery queryDto = new DictionaryQuery();
        queryDto.setCode(dto.getCode());
        Dictionary existedOne = dao.selectOne(queryDto);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Dictionary already exists");
    }

    protected void ensureItemUnique(DictionaryItemDto dto) {
        DictionaryItemQuery query = DictionaryItemQuery.builder()
                .code(dto.getCode())
                .dictionaryCode(dto.getDictionaryCode())
                .build();
        DictionaryItem existedOne = itemDao.selectOne(query);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Dictionary item already exists");
    }
}
