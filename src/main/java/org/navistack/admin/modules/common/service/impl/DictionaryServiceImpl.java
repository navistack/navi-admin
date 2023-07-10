package org.navistack.admin.modules.common.service.impl;

import org.navistack.admin.modules.common.dao.DictionaryDao;
import org.navistack.admin.modules.common.dao.DictionaryItemDao;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.common.service.DictionaryService;
import org.navistack.admin.modules.common.service.convert.DictionaryConverter;
import org.navistack.admin.modules.common.service.convert.DictionaryItemConverter;
import org.navistack.admin.modules.common.service.dto.DictionaryDto;
import org.navistack.admin.modules.common.service.dto.DictionaryItemDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.Asserts;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryDao dao;

    private final DictionaryItemDao itemDao;

    public DictionaryServiceImpl(DictionaryDao dao, DictionaryItemDao itemDao) {
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
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Dictionary code has been taken already"));

        Dictionary entity = DictionaryConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(DictionaryDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Dictionary does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Dictionary code has been taken already"));

        Dictionary entity = DictionaryConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById, () -> new NoSuchEntityException("Dictionary does not exist"));
        Asserts.state(id, this::validateAbsenceOfItem, () -> new ConstraintViolationException("Dictionary can not be removed as item(s) exist(s)"));
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
        Asserts.state(dto.getDictionaryId(), this::validateExistenceById, () -> new NoSuchEntityException("Dictionary does not exist"));
        Asserts.state(this.validateItemAvailabilityOfCode(dto.getCode(), dto.getDictionaryId(), dto.getId()), () -> new DomainValidationException("Dictionary item code has been taken already"));

        DictionaryItem entity = DictionaryItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        itemDao.insert(entity);
    }

    @Override
    public void modifyItem(DictionaryItemDto dto) {
        Asserts.state(dto.getId(), this::validateItemExistenceById, () -> new NoSuchEntityException("Dictionary item does not exist"));
        Asserts.state(dto.getDictionaryId(), this::validateExistenceById, () -> new NoSuchEntityException("Dictionary does not exist"));
        Asserts.state(this.validateItemAvailabilityOfCode(dto.getCode(), dto.getDictionaryId(), dto.getId()), () -> new DomainValidationException("Dictionary item code has been taken already"));

        DictionaryItem entity = DictionaryItemConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        itemDao.updateById(entity);
    }

    @Override
    public void removeItem(Long id) {
        Asserts.state(id, this::validateItemExistenceById, () -> new NoSuchEntityException("Dictionary item does not exist"));
        itemDao.deleteById(id);
    }


    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.selectOneById(id) != null;
    }

    protected boolean validateAvailabilityOfCode(String code, Long modifiedId) {
        DictionaryQuery query = DictionaryQuery.builder().code(code).build();
        Dictionary existingOne = dao.selectOne(query);

        if (existingOne == null) {
            return true;
        }

        if (!existingOne.getCode().equals(code)) {
            return true;
        }

        if (existingOne.getId().equals(modifiedId)) {
            return true;
        }

        return false;
    }

    protected boolean validateItemExistenceById(Long id) {
        return id != null && itemDao.selectOneById(id) != null;
    }

    protected boolean validateItemAvailabilityOfCode(String itemCode, Long dictionaryId, Long modifiedId) {
        DictionaryItemQuery query = DictionaryItemQuery.builder().code(itemCode).dictionaryId(dictionaryId).build();
        DictionaryItem existingOne = itemDao.selectOne(query);

        if (existingOne == null) {
            return true;
        }

        if (!existingOne.getCode().equals(itemCode)) {
            return true;
        }

        if (existingOne.getId().equals(modifiedId)) {
            return true;
        }

        return false;
    }

    protected boolean validateAbsenceOfItem(Long dictionaryId) {
        DictionaryItemQuery query = DictionaryItemQuery.builder().dictionaryId(dictionaryId).build();
        return itemDao.count(query) <= 0;
    }

    // endregion
}
