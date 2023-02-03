package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.DictDao;
import org.navistack.admin.modules.common.dao.DictItemDao;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.common.query.DictItemQuery;
import org.navistack.admin.modules.common.query.DictQuery;
import org.navistack.admin.modules.mgmt.service.DictService;
import org.navistack.admin.modules.mgmt.service.convert.DictConverter;
import org.navistack.admin.modules.mgmt.service.convert.DictItemConverter;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
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
public class DictServiceImpl implements DictService {
    private final DictDao dao;

    private final DictItemDao itemDao;

    public DictServiceImpl(
            DictDao dao,
            DictItemDao itemDao
    ) {
        this.dao = dao;
        this.itemDao = itemDao;
    }

    @Override
    public Page<DictDto> paginate(DictQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<Dict> entities = dao.selectWithPageable(query, pageable);
        Collection<DictDto> dtos = DictConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(DictDto dto) {
        ensureUnique(dto);

        Dict entity = DictConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(DictDto dto) {
        ensureUnique(dto);

        Dict entity = DictConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }



    @Override
    public Page<DictItemDto> paginateItem(DictItemQuery query, Pageable pageable) {
        long totalRecords = itemDao.count(query);
        List<DictItem> entities = itemDao.selectWithPageable(query, pageable);
        Collection<DictItemDto> dtos = DictItemConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void createItem(DictItemDto dto) {
        ensureItemUnique(dto);

        DictItem entity = DictItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        itemDao.insert(entity);
    }

    @Override
    public void modifyItem(DictItemDto dto) {
        ensureItemUnique(dto);

        DictItem entity = DictItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        itemDao.updateById(entity);
    }

    @Override
    public void removeItem(Long id) {
        itemDao.deleteById(id);
    }

    protected void ensureUnique(DictDto dto) {
        DictQuery queryDto = new DictQuery();
        queryDto.setCode(dto.getCode());
        Dict existedOne = dao.selectOne(queryDto);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Dict already exists");
    }

    protected void ensureItemUnique(DictItemDto dto) {
        DictItemQuery query = DictItemQuery.builder()
                .itKey(dto.getItKey())
                .build();
        DictItem existedOne = itemDao.selectOne(query);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Dict item already exists");
    }
}
