package org.navistack.admin.modules.mgmt.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.utils.GenericBuilder;

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
        when(dao.selectOne(
                RegionQuery.builder()
                        .code("RG")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 1L)
                        .set(Region::setCode, "RG")
                        .set(Region::setName, "Root Region")
                        .build()
        );
        when(dao.selectOne(
                RegionQuery.builder()
                        .code("RG-01")
                        .build()
        )).thenReturn(
                null
        );
        RegionDto dto = new RegionDto();
        dto.setCode("RG-01");
        dto.setName("Sub-region 01");
        dto.setParentCode("RG");
        service.create(dto);
        verify(dao, times(1)).insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                RegionQuery.builder()
                        .code("RG-01")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 2L)
                        .set(Region::setCode, "RG-01")
                        .set(Region::setName, "Sub-region 01")
                        .set(Region::setParentCode, "RG")
                        .build()
        );
        assertThatThrownBy(() -> {
            RegionDto dto = new RegionDto();
            dto.setCode("RG-01");
            dto.setName("Sub-region 01");
            dto.setParentCode("RG");
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        assertThatThrownBy(() -> {
            RegionDto dto = new RegionDto();
            dto.setCode("RG-02");
            dto.setName("Sub-region 02");
            service.create(dto);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            RegionDto dto = new RegionDto();
            dto.setCode("RG-02");
            dto.setName("Sub-region 02");
            dto.setParentCode("RH");
            service.create(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.selectOne(
                RegionQuery.builder()
                        .code("RG")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 1L)
                        .set(Region::setCode, "RG")
                        .set(Region::setName, "Root Region")
                        .build()
        );
        when(dao.selectOne(
                RegionQuery.builder()
                        .code("RG-02")
                        .build()
        )).thenReturn(
                null
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 2L)
                        .set(Region::setCode, "RG-01")
                        .set(Region::setName, "Sub-region 01")
                        .set(Region::setParentCode, "RG")
                        .build()
        );
        RegionDto dto = new RegionDto();
        dto.setId(2L);
        dto.setCode("RG-02");
        dto.setName("Sub-region 02");
        dto.setParentCode("RG");
        service.modify(dto);
        verify(dao, times(1)).updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                RegionQuery.builder()
                        .code("RG")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 1L)
                        .set(Region::setCode, "RG")
                        .set(Region::setName, "Root Region")
                        .build()
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 2L)
                        .set(Region::setCode, "RG-01")
                        .set(Region::setName, "Sub-region 01")
                        .set(Region::setParentCode, "RG")
                        .build()
        );
        assertThatThrownBy(() -> {
            RegionDto dto = new RegionDto();
            dto.setId(2L);
            dto.setCode("RG");
            dto.setName("Sub-region 02");
            dto.setParentCode("RG");
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 2L)
                        .set(Region::setCode, "RG-01")
                        .set(Region::setName, "Sub-region 01")
                        .set(Region::setParentCode, "RG")
                        .build()
        );
        assertThatThrownBy(() -> {
            RegionDto dto = new RegionDto();
            dto.setId(2L);
            dto.setCode("RG-03");
            dto.setName("Sub-region 03");
            service.modify(dto);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            RegionDto dto = new RegionDto();
            dto.setId(2L);
            dto.setCode("RG-03");
            dto.setName("Sub-region 03");
            dto.setParentCode("RG");
            service.modify(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 2L)
                        .set(Region::setCode, "RG-01")
                        .set(Region::setName, "Sub-region 01")
                        .set(Region::setParentCode, "RG")
                        .build()
        );
        service.remove(2L);
        verify(dao, times(1)).deleteById(any());
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenSubordinateExists() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Region::new)
                        .set(Region::setId, 1L)
                        .set(Region::setCode, "RG")
                        .set(Region::setName, "Root Region")
                        .build()
        );
        when(dao.count(
                RegionQuery.builder()
                        .parentCode("RG")
                        .build()
        )).thenReturn(1L);
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
}
