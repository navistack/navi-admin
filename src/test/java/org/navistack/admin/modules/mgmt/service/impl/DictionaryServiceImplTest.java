package org.navistack.admin.modules.mgmt.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.common.dao.DictionaryDao;
import org.navistack.admin.modules.common.dao.DictionaryItemDao;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryDto;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryItemDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.utils.GenericBuilder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DictionaryServiceImplTest {
    @InjectMocks
    private DictionaryServiceImpl service;

    @Mock
    private DictionaryDao dao;

    @Mock
    private DictionaryItemDao itemDao;

    @Test
    void create_shouldCreateSuccessfully() {
        DictionaryDto dto = new DictionaryDto();
        dto.setCode("TCP_STATE");
        dto.setName("TCP State");
        service.create(dto);
        verify(dao, times(1)).insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                DictionaryQuery.builder()
                        .code("TCP_STATE")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        assertThatThrownBy(() -> {
            DictionaryDto dto = new DictionaryDto();
            dto.setCode("TCP_STATE");
            dto.setName("TCP State");
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        DictionaryDto dto = new DictionaryDto();
        dto.setId(1L);
        dto.setCode("TCP_STATE");
        dto.setName("TCP States");
        service.modify(dto);
        verify(dao, times(1)).updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                DictionaryQuery.builder()
                        .code("TCP_STATE")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 2L)
                        .set(Dictionary::setCode, "TCP_STATES")
                        .set(Dictionary::setName, "TCP States")
                        .build()
        );
        assertThatThrownBy(() -> {
            DictionaryDto dto = new DictionaryDto();
            dto.setId(2L);
            dto.setCode("TCP_STATE");
            dto.setName("TCP State");
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        service.remove(1L);
        verify(dao, times(1)).deleteById(any());
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenItemExists() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        when(itemDao.count(
                DictionaryItemQuery.builder()
                        .dictionaryId(1L)
                        .build()
        )).thenReturn(11L);
        assertThatThrownBy(() -> {
            service.remove(1L);
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void remove_shouldThrowNoSuchEntityExceptionWhenIdIsWrong() {
        assertThatThrownBy(() -> {
            service.remove(null);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            service.remove(100L);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void createItem_shouldCreateSuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        DictionaryItemDto dto = new DictionaryItemDto();
        dto.setCode("LISTEN");
        dto.setName("Listen");
        dto.setDictionaryId(1L);
        service.createItem(dto);
        verify(itemDao, times(1)).insert(any());
    }

    @Test
    void createItem_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        when(itemDao.selectOne(
                DictionaryItemQuery.builder()
                        .code("LISTEN")
                        .dictionaryId(1L)
                        .build()
        )).thenReturn(
                GenericBuilder.of(DictionaryItem::new)
                        .set(DictionaryItem::setId, 1L)
                        .set(DictionaryItem::setCode, "LISTEN")
                        .set(DictionaryItem::setName, "Listen")
                        .build()
        );
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(1L);
            service.createItem(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void createItem_shouldThrowNoSuchEntityExceptionWhenDictionaryIdIsWrong() {
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(100L);
            service.createItem(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modifyItem_shouldModifySuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        when(itemDao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(DictionaryItem::new)
                        .set(DictionaryItem::setId, 1L)
                        .set(DictionaryItem::setCode, "LISTEN")
                        .set(DictionaryItem::setName, "Listen")
                        .build()
        );
        DictionaryItemDto dto = new DictionaryItemDto();
        dto.setId(1L);
        dto.setCode("LISTEN");
        dto.setName("Listen");
        dto.setDictionaryId(1L);
        service.modifyItem(dto);
        verify(itemDao, times(1)).updateById(any());
    }

    @Test
    void modifyItem_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Dictionary::new)
                        .set(Dictionary::setId, 1L)
                        .set(Dictionary::setCode, "TCP_STATE")
                        .set(Dictionary::setName, "TCP State")
                        .build()
        );
        when(itemDao.selectOne(
                DictionaryItemQuery.builder()
                        .code("LISTEN")
                        .dictionaryId(1L)
                        .build()
        )).thenReturn(
                GenericBuilder.of(DictionaryItem::new)
                        .set(DictionaryItem::setId, 1L)
                        .set(DictionaryItem::setCode, "LISTEN")
                        .set(DictionaryItem::setName, "Listen")
                        .build()
        );
        when(itemDao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(DictionaryItem::new)
                        .set(DictionaryItem::setId, 2L)
                        .set(DictionaryItem::setCode, "SYN-SENT")
                        .set(DictionaryItem::setName, "SYNCHRONIZATION SENT")
                        .build()
        );
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setId(2L);
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(1L);
            service.modifyItem(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modifyItem_shouldThrowNoSuchEntityExceptionWhenDictionaryIdIsWrong() {
        when(itemDao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(DictionaryItem::new)
                        .set(DictionaryItem::setId, 1L)
                        .set(DictionaryItem::setCode, "LISTEN")
                        .set(DictionaryItem::setName, "Listen")
                        .build()
        );
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setId(1L);
            dto.setCode("LISTEN");
            dto.setName("Listen");
            service.modifyItem(dto);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setId(1L);
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(100L);
            service.modifyItem(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void removeItem_shouldRemoveSuccessfully() {
        when(itemDao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(DictionaryItem::new)
                        .set(DictionaryItem::setId, 1L)
                        .set(DictionaryItem::setCode, "LISTEN")
                        .set(DictionaryItem::setName, "Listen")
                        .build()
        );
        service.removeItem(1L);
        verify(itemDao, times(1)).deleteById(any());
    }

    @Test
    void removeItem_shouldThrowNoSuchEntityExceptionWhenIdIsWrong() {
        assertThatThrownBy(() -> {
            service.removeItem(null);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            service.removeItem(100L);
        }).isInstanceOf(NoSuchEntityException.class);
    }
}
