
package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.OrganizationDao;
import org.navistack.admin.modules.identity.service.dto.OrganizationCreateDto;
import org.navistack.admin.modules.identity.service.dto.OrganizationModifyDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {
    @InjectMocks
    private OrganizationServiceImpl service;

    @Mock
    private OrganizationDao dao;

    @Test
    void create_shouldCreateSuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByCode("TK03"))
                .thenReturn(null);
        OrganizationCreateDto dto = new OrganizationCreateDto();
        dto.setCode("TK03");
        dto.setName("Sub-organization 02");
        dto.setSuperId(1L);
        service.create(dto);
        verify(dao, times(1))
                .insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectIdByCode("TK02"))
                .thenReturn(2L);
        assertThatThrownBy(() -> {
            OrganizationCreateDto dto = new OrganizationCreateDto();
            dto.setCode("TK02");
            dto.setName("Sub-organization 01");
            dto.setSuperId(1L);
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.selectIdByCode("TK03"))
                .thenReturn(null);
        when(dao.existsById(0L))
                .thenReturn(false);
        assertThatThrownBy(() -> {
            OrganizationCreateDto dto = new OrganizationCreateDto();
            dto.setCode("TK03");
            dto.setName("Sub-organization 02");
            service.create(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            OrganizationCreateDto dto = new OrganizationCreateDto();
            dto.setCode("TK03");
            dto.setName("Sub-organization 02");
            dto.setSuperId(0L);
            service.create(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("TK04"))
                .thenReturn(2L);
        when(dao.existsById(1L))
                .thenReturn(true);
        OrganizationModifyDto dto = new OrganizationModifyDto();
        dto.setId(2L);
        dto.setCode("TK04");
        dto.setName("Sub-organization 03");
        dto.setSuperId(1L);
        service.modify(dto);
        verify(dao, times(1))
                .updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("TK01"))
                .thenReturn(3L);
        assertThatThrownBy(() -> {
            OrganizationModifyDto dto = new OrganizationModifyDto();
            dto.setId(2L);
            dto.setCode("TK01");
            dto.setName("Sub-organization 03");
            dto.setSuperId(1L);
            service.modify(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("TK04"))
                .thenReturn(null);
        when(dao.existsById(0L))
                .thenReturn(false);
        assertThatThrownBy(() -> {
            OrganizationModifyDto dto = new OrganizationModifyDto();
            dto.setId(2L);
            dto.setCode("TK04");
            dto.setName("Sub-organization 03");
            service.modify(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            OrganizationModifyDto dto = new OrganizationModifyDto();
            dto.setId(2L);
            dto.setCode("TK04");
            dto.setName("Sub-organization 03");
            dto.setSuperId(0L);
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
        when(dao.existsBySuperId(1L))
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
