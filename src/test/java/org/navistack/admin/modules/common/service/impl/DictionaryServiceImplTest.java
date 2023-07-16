package org.navistack.admin.modules.common.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.common.dao.DictionaryDao;
import org.navistack.admin.modules.common.dao.DictionaryItemDao;
import org.navistack.admin.modules.common.service.dto.DictionaryDto;
import org.navistack.admin.modules.common.service.dto.DictionaryItemDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;

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
        when(dao.selectIdByCode("TCP_STATE"))
                .thenReturn(null);
        DictionaryDto dto = new DictionaryDto();
        dto.setCode("TCP_STATE");
        dto.setName("TCP State");
        service.create(dto);
        verify(dao, times(1))
                .insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectIdByCode("TCP_STATE"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            DictionaryDto dto = new DictionaryDto();
            dto.setCode("TCP_STATE");
            dto.setName("TCP State");
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByCode("TCP_STATE"))
                .thenReturn(1L);
        DictionaryDto dto = new DictionaryDto();
        dto.setId(1L);
        dto.setCode("TCP_STATE");
        dto.setName("TCP States");
        service.modify(dto);
        verify(dao, times(1))
                .updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("TCP_STATE"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            DictionaryDto dto = new DictionaryDto();
            dto.setId(2L);
            dto.setCode("TCP_STATE");
            dto.setName("TCP State");
            service.modify(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        service.remove(1L);
        verify(dao, times(1))
                .deleteById(any());
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenItemExists() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(itemDao.existsByDictionaryId(1L))
                .thenReturn(true);
        assertThatThrownBy(() -> service.remove(1L))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void remove_shouldThrowNoSuchEntityExceptionWhenIdIsWrong() {
        assertThatThrownBy(() -> service.remove(null))
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> service.remove(100L))
                .isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void createItem_shouldCreateSuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(itemDao.selectIdByCodeAndDictionaryId("LISTEN", 1L))
                .thenReturn(null);
        DictionaryItemDto dto = new DictionaryItemDto();
        dto.setCode("LISTEN");
        dto.setName("Listen");
        dto.setDictionaryId(1L);
        service.createItem(dto);
        verify(itemDao, times(1))
                .insert(any());
    }

    @Test
    void createItem_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(itemDao.selectIdByCodeAndDictionaryId("LISTEN", 1L))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(1L);
            service.createItem(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void createItem_shouldThrowNoSuchEntityExceptionWhenDictionaryIdIsWrong() {
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(100L);
            service.createItem(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modifyItem_shouldModifySuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(itemDao.existsById(1L))
                .thenReturn(true);
        when(itemDao.selectIdByCodeAndDictionaryId("LISTEN", 1L))
                .thenReturn(1L);
        DictionaryItemDto dto = new DictionaryItemDto();
        dto.setId(1L);
        dto.setCode("LISTEN");
        dto.setName("Listen");
        dto.setDictionaryId(1L);
        service.modifyItem(dto);
        verify(itemDao, times(1))
                .updateById(any());
    }

    @Test
    void modifyItem_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(itemDao.existsById(2L))
                .thenReturn(true);
        when(itemDao.selectIdByCodeAndDictionaryId("LISTEN", 1L))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setId(2L);
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(1L);
            service.modifyItem(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modifyItem_shouldThrowNoSuchEntityExceptionWhenDictionaryIdIsWrong() {
        when(itemDao.existsById(1L))
                .thenReturn(true);
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setId(1L);
            dto.setCode("LISTEN");
            dto.setName("Listen");
            service.modifyItem(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setId(1L);
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryId(100L);
            service.modifyItem(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void removeItem_shouldRemoveSuccessfully() {
        when(itemDao.existsById(1L))
                .thenReturn(true);
        service.removeItem(1L);
        verify(itemDao, times(1))
                .deleteById(any());
    }

    @Test
    void removeItem_shouldThrowNoSuchEntityExceptionWhenIdIsWrong() {
        assertThatThrownBy(() -> service.removeItem(null))
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> service.removeItem(100L))
                .isInstanceOf(NoSuchEntityException.class);
    }
}
