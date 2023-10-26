package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.service.dto.PrivilegeCreateDto;
import org.navistack.admin.modules.identity.service.dto.PrivilegeModifyDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PrivilegeServiceImplTest {
    @InjectMocks
    private PrivilegeServiceImpl service;

    @Mock
    private PrivilegeDao dao;

    @Test
    void create_shouldCreateSuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByCode("sys:privilege:create"))
                .thenReturn(null);
        PrivilegeCreateDto dto = new PrivilegeCreateDto();
        dto.setCode("sys:privilege:create");
        dto.setName("Create new record of privilege");
        dto.setParentId(1L);
        service.create(dto);
        verify(dao, times(1))
                .insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectIdByCode("sys:privilege:query"))
                .thenReturn(2L);
        assertThatThrownBy(() -> {
            PrivilegeCreateDto dto = new PrivilegeCreateDto();
            dto.setCode("sys:privilege:query");
            dto.setName("Query information on Privilege");
            dto.setParentId(1L);
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.selectIdByCode("sys:privilege:create"))
                .thenReturn(null);
        when(dao.existsById(0L))
                .thenReturn(false);
        assertThatThrownBy(() -> {
            PrivilegeCreateDto dto = new PrivilegeCreateDto();
            dto.setCode("sys:privilege:create");
            dto.setName("Create new record of privilege");
            service.create(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            PrivilegeCreateDto dto = new PrivilegeCreateDto();
            dto.setCode("sys:privilege:create");
            dto.setName("Create new record of privilege");
            dto.setParentId(0L);
            service.create(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("sys:privilege:enumerate"))
                .thenReturn(null);
        when(dao.existsById(1L))
                .thenReturn(true);
        PrivilegeModifyDto dto = new PrivilegeModifyDto();
        dto.setId(2L);
        dto.setCode("sys:privilege:enumerate");
        dto.setName("Enumerate privileges");
        dto.setParentId(1L);
        service.modify(dto);
        verify(dao, times(1))
                .updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("sys:privilege"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            PrivilegeModifyDto dto = new PrivilegeModifyDto();
            dto.setId(2L);
            dto.setCode("sys:privilege");
            dto.setName("Enumerate privileges");
            dto.setParentId(1L);
            service.modify(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("sys:privilege:enumerate"))
                .thenReturn(null);
        when(dao.existsById(0L))
                .thenReturn(false);
        assertThatThrownBy(() -> {
            PrivilegeModifyDto dto = new PrivilegeModifyDto();
            dto.setId(2L);
            dto.setCode("sys:privilege:enumerate");
            dto.setName("Enumerate privileges");
            dto.setParentId(0L);
            service.modify(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.existsById(2L))
                .thenReturn(true);
        service.remove(2L);
        verify(dao, times(1))
                .deleteById(any());
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenSubordinateExists() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.existsByParentId(1L))
                .thenReturn(true);
        assertThatThrownBy(() -> service.remove(1L))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void remove_shouldThrowNoSuchEntityExceptionWhenIdIsWrong() {
        when(dao.existsById(100L))
                .thenReturn(false);
        assertThatThrownBy(() -> service.remove(null))
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> service.remove(100L))
                .isInstanceOf(NoSuchEntityException.class);
    }
}
