package org.navistack.admin.modules.common.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.service.dto.RegionCreateDto;
import org.navistack.admin.modules.common.service.dto.RegionModifyDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {
    @InjectMocks
    private RegionServiceImpl service;

    @Mock
    private RegionDao dao;

    @Test
    void create_shouldCreateSuccessfully() {
        when(dao.existsByCode("RG"))
                .thenReturn(true);
        when(dao.selectIdByCode("RG-01"))
                .thenReturn(null);
        RegionCreateDto dto = new RegionCreateDto();
        dto.setCode("RG-01");
        dto.setName("Sub-region 01");
        dto.setParentCode("RG");
        service.create(dto);
        verify(dao, times(1))
                .insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectIdByCode("RG-01"))
                .thenReturn(2L);
        assertThatThrownBy(() -> {
            RegionCreateDto dto = new RegionCreateDto();
            dto.setCode("RG-01");
            dto.setName("Sub-region 01");
            dto.setParentCode("RG");
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.selectIdByCode("RG-02"))
                .thenReturn(null);
        assertThatThrownBy(() -> {
            RegionCreateDto dto = new RegionCreateDto();
            dto.setCode("RG-02");
            dto.setName("Sub-region 02");
            service.create(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            RegionCreateDto dto = new RegionCreateDto();
            dto.setCode("RG-02");
            dto.setName("Sub-region 02");
            dto.setParentCode("RH");
            service.create(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.existsByCode("RG"))
                .thenReturn(true);
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("RG-02"))
                .thenReturn(null);
        RegionModifyDto dto = new RegionModifyDto();
        dto.setId(2L);
        dto.setCode("RG-02");
        dto.setName("Sub-region 02");
        dto.setParentCode("RG");
        service.modify(dto);
        verify(dao, times(1))
                .updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("RG"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            RegionModifyDto dto = new RegionModifyDto();
            dto.setId(2L);
            dto.setCode("RG");
            dto.setName("Sub-region 02");
            dto.setParentCode("RG");
            service.modify(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("RG-03"))
                .thenReturn(2L);
        assertThatThrownBy(() -> {
            RegionModifyDto dto = new RegionModifyDto();
            dto.setId(2L);
            dto.setCode("RG-03");
            dto.setName("Sub-region 03");
            service.modify(dto);
        })
                .isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            RegionModifyDto dto = new RegionModifyDto();
            dto.setId(2L);
            dto.setCode("RG-03");
            dto.setName("Sub-region 03");
            dto.setParentCode("RG");
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
        when(dao.selectCodeById(1L))
                .thenReturn("RG");
        when(dao.existsByParentCode("RG"))
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
}
