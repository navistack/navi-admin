package org.navistack.admin.modules.mgmt.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.common.dao.DictionaryDao;
import org.navistack.admin.modules.common.dao.DictionaryItemDao;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryDto;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryItemDto;
import org.navistack.framework.core.error.EntityDuplicationException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        dto.setName("TCP States");
        service.create(dto);
        verify(dao, times(1)).insert(any());
    }

    @Test
    void create_shouldThrowEntityDuplicationExceptionWhenCodeDuplicated() {
        Dictionary entity = new Dictionary();
        entity.setId(1L);
        entity.setCode("TCP_STATE");
        entity.setName("TCP States");
        Mockito.when(dao.selectOne(any())).thenReturn(entity);
        Assertions.assertThatThrownBy(() -> {
            DictionaryDto dto = new DictionaryDto();
            dto.setCode("TCP_STATE");
            dto.setName("TCP States");
            service.create(dto);
        }).isInstanceOf(EntityDuplicationException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        Dictionary entity = new Dictionary();
        entity.setId(1L);
        entity.setCode("TCP_STATE");
        entity.setName("TCP States");
        DictionaryDto dto = new DictionaryDto();
        dto.setId(1L);
        dto.setCode("TCP_STATES");
        dto.setName("TCP States");
        service.modify(dto);
        verify(dao, times(1)).updateById(any());
    }

    @Test
    void modify_shouldThrowEntityDuplicationExceptionWhenCodeDuplicated() {
        Dictionary entity = new Dictionary();
        entity.setId(1L);
        entity.setCode("TCP_STATE");
        entity.setName("TCP States");
        Mockito.when(dao.selectOne(any())).thenReturn(entity);
        Assertions.assertThatThrownBy(() -> {
            DictionaryDto dto = new DictionaryDto();
            dto.setId(2L);
            dto.setCode("TCP_STATE");
            dto.setName("TCP States");
            service.modify(dto);
        }).isInstanceOf(EntityDuplicationException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        service.remove(1L);
        verify(dao, times(1)).deleteById(any());
    }


    @Test
    void createItem_shouldCreateSuccessfully() {
        DictionaryItemDto dto = new DictionaryItemDto();
        dto.setCode("LISTEN");
        dto.setName("Listen");
        dto.setDictionaryCode("TCP_STATE");
        service.createItem(dto);
        verify(itemDao, times(1)).insert(any());
    }

    @Test
    void createItem_shouldThrowEntityDuplicationExceptionWhenCodeDuplicated() {
        DictionaryItem entity = new DictionaryItem();
        entity.setId(1L);
        entity.setCode("LISTEN");
        entity.setName("Listen");
        entity.setDictionaryCode("TCP_STATE");
        Mockito.when(itemDao.selectOne(any())).thenReturn(entity);
        Assertions.assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryCode("TCP_STATE");
            service.createItem(dto);
        }).isInstanceOf(EntityDuplicationException.class);
    }

    @Test
    void modifyItem_shouldModifySuccessfully() {
        DictionaryItem entity = new DictionaryItem();
        entity.setId(1L);
        entity.setCode("LISTEN");
        entity.setName("Listen");
        entity.setDictionaryCode("TCP_STATE");
        DictionaryItemDto dto = new DictionaryItemDto();
        dto.setCode("LISTEN");
        dto.setName("Listen");
        dto.setDictionaryCode("TCP_STATE");
        service.modifyItem(dto);
        verify(itemDao, times(1)).updateById(any());
    }

    @Test
    void modifyItem_shouldThrowEntityDuplicationExceptionWhenCodeDuplicated() {
        DictionaryItem entity = new DictionaryItem();
        entity.setId(1L);
        entity.setCode("LISTEN");
        entity.setName("Listen");
        entity.setDictionaryCode("TCP_STATE");
        Mockito.when(itemDao.selectOne(any())).thenReturn(entity);
        Assertions.assertThatThrownBy(() -> {
            DictionaryItemDto dto = new DictionaryItemDto();
            dto.setId(2L);
            dto.setCode("LISTEN");
            dto.setName("Listen");
            dto.setDictionaryCode("TCP_STATE");
            service.modifyItem(dto);
        }).isInstanceOf(EntityDuplicationException.class);
    }

    @Test
    void removeItem_shouldRemoveSuccessfully() {
        service.removeItem(1L);
        verify(itemDao, times(1)).deleteById(any());
    }
}
