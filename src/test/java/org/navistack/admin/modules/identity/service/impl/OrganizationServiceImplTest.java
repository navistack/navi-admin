package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.OrganizationDao;
import org.navistack.admin.modules.identity.entity.Organization;
import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.admin.modules.identity.service.dto.OrganizationDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.utils.GenericBuilder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {
    @InjectMocks
    private OrganizationServiceImpl service;

    @Mock
    private OrganizationDao dao;

    @Test
    void create_shouldCreateSuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 1L)
                        .set(Organization::setCode, "TK01")
                        .set(Organization::setName, "Root Organization")
                        .build()
        );
        OrganizationDto dto = new OrganizationDto();
        dto.setCode("TK03");
        dto.setName("Sub-organization 02");
        dto.setSuperId(1L);
        service.create(dto);
        verify(dao, times(1)).insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                OrganizationQuery.builder()
                        .code("TK02")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 2L)
                        .set(Organization::setCode, "TK02")
                        .set(Organization::setName, "Sub-organization 01")
                        .set(Organization::setSuperId, 1L)
                        .build()
        );
        assertThatThrownBy(() -> {
            OrganizationDto dto = new OrganizationDto();
            dto.setCode("TK02");
            dto.setName("Sub-organization 01");
            dto.setSuperId(1L);
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        assertThatThrownBy(() -> {
            OrganizationDto dto = new OrganizationDto();
            dto.setCode("TK03");
            dto.setName("Sub-organization 02");
            service.create(dto);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            OrganizationDto dto = new OrganizationDto();
            dto.setCode("TK03");
            dto.setName("Sub-organization 02");
            dto.setSuperId(0L);
            service.create(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 1L)
                        .set(Organization::setCode, "TK01")
                        .set(Organization::setName, "Root Organization")
                        .build()
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 2L)
                        .set(Organization::setCode, "TK02")
                        .set(Organization::setName, "Sub-organization 01")
                        .set(Organization::setSuperId, 1L)
                        .build()
        );
        OrganizationDto dto = new OrganizationDto();
        dto.setId(2L);
        dto.setCode("TK04");
        dto.setName("Sub-organization 03");
        dto.setSuperId(1L);
        service.modify(dto);
        verify(dao, times(1)).updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                OrganizationQuery.builder()
                        .code("TK01")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 1L)
                        .set(Organization::setCode, "TK01")
                        .set(Organization::setName, "Root Organization")
                        .build()
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 2L)
                        .set(Organization::setCode, "TK02")
                        .set(Organization::setName, "Sub-organization 01")
                        .set(Organization::setSuperId, 1L)
                        .build()
        );
        assertThatThrownBy(() -> {
            OrganizationDto dto = new OrganizationDto();
            dto.setId(2L);
            dto.setCode("TK01");
            dto.setName("Sub-organization 03");
            dto.setSuperId(1L);
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 2L)
                        .set(Organization::setCode, "TK02")
                        .set(Organization::setName, "Sub-organization 01")
                        .set(Organization::setSuperId, 1L)
                        .build()
        );
        assertThatThrownBy(() -> {
            OrganizationDto dto = new OrganizationDto();
            dto.setId(2L);
            dto.setCode("TK04");
            dto.setName("Sub-organization 03");
            service.modify(dto);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            OrganizationDto dto = new OrganizationDto();
            dto.setId(2L);
            dto.setCode("TK04");
            dto.setName("Sub-organization 03");
            dto.setSuperId(0L);
            service.modify(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 2L)
                        .set(Organization::setCode, "TK02")
                        .set(Organization::setName, "Sub-organization 01")
                        .set(Organization::setSuperId, 1L)
                        .build()
        );
        service.remove(2L);
        verify(dao, times(1)).deleteById(any());
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenSubordinateExists() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Organization::new)
                        .set(Organization::setId, 1L)
                        .set(Organization::setCode, "TK01")
                        .set(Organization::setName, "Root Organization")
                        .build()
        );
        when(dao.count(
                OrganizationQuery.builder()
                        .superId(1L)
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
