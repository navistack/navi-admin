package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.utils.GenericBuilder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PrivilegeServiceImplTest {
    @InjectMocks
    private PrivilegeServiceImpl service;

    @Mock
    private PrivilegeDao dao;

    @Test
    void create_shouldCreateSuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 1L)
                        .set(Privilege::setCode, "sys:privilege")
                        .set(Privilege::setName, "Privileges about privileges")
                        .build()
        );
        PrivilegeDto dto = new PrivilegeDto();
        dto.setCode("sys:privilege:create");
        dto.setName("Create new entity of privilege");
        dto.setParentId(1L);
        service.create(dto);
        verify(dao, times(1)).insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                PrivilegeQuery.builder()
                        .code("sys:privilege:query")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 2L)
                        .set(Privilege::setCode, "sys:privilege:query")
                        .set(Privilege::setName, "Query information on Privilege")
                        .set(Privilege::setParentId, 1L)
                        .build()
        );
        assertThatThrownBy(() -> {
            PrivilegeDto dto = new PrivilegeDto();
            dto.setCode("sys:privilege:query");
            dto.setName("Query information on Privilege");
            dto.setParentId(1L);
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        assertThatThrownBy(() -> {
            PrivilegeDto dto = new PrivilegeDto();
            dto.setCode("sys:privilege:create");
            dto.setName("Create new entity of privilege");
            service.create(dto);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            PrivilegeDto dto = new PrivilegeDto();
            dto.setCode("sys:privilege:create");
            dto.setName("Create new entity of privilege");
            dto.setParentId(0L);
            service.create(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 1L)
                        .set(Privilege::setCode, "sys:privilege")
                        .set(Privilege::setName, "Privileges about privileges")
                        .build()
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 2L)
                        .set(Privilege::setCode, "sys:privilege:query")
                        .set(Privilege::setName, "Query information on Privilege")
                        .set(Privilege::setParentId, 1L)
                        .build()
        );
        PrivilegeDto dto = new PrivilegeDto();
        dto.setId(2L);
        dto.setCode("sys:privilege:enumerate");
        dto.setName("Enumerate privileges");
        dto.setParentId(1L);
        service.modify(dto);
        verify(dao, times(1)).updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                PrivilegeQuery.builder()
                        .code("sys:privilege")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 1L)
                        .set(Privilege::setCode, "sys:privilege")
                        .set(Privilege::setName, "Privileges about privileges")
                        .build()
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 2L)
                        .set(Privilege::setCode, "sys:privilege:query")
                        .set(Privilege::setName, "Query information on Privilege")
                        .set(Privilege::setParentId, 1L)
                        .build()
        );
        assertThatThrownBy(() -> {
            PrivilegeDto dto = new PrivilegeDto();
            dto.setId(2L);
            dto.setCode("sys:privilege");
            dto.setName("Enumerate privileges");
            dto.setParentId(1L);
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowNoSuchEntityExceptionWhenParentIdIsWrong() {
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 2L)
                        .set(Privilege::setCode, "sys:privilege:query")
                        .set(Privilege::setName, "Query information on Privilege")
                        .set(Privilege::setParentId, 1L)
                        .build()
        );
        assertThatThrownBy(() -> {
            PrivilegeDto dto = new PrivilegeDto();
            dto.setId(2L);
            dto.setCode("sys:privilege:enumerate");
            dto.setName("Enumerate privileges");
            service.modify(dto);
        }).isInstanceOf(NoSuchEntityException.class);
        assertThatThrownBy(() -> {
            PrivilegeDto dto = new PrivilegeDto();
            dto.setId(2L);
            dto.setCode("sys:privilege:enumerate");
            dto.setName("Enumerate privileges");
            dto.setParentId(0L);
            service.modify(dto);
        }).isInstanceOf(NoSuchEntityException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 2L)
                        .set(Privilege::setCode, "sys:privilege:query")
                        .set(Privilege::setName, "Query information on Privilege")
                        .set(Privilege::setParentId, 1L)
                        .build()
        );
        service.remove(2L);
        verify(dao, times(1)).deleteById(any());
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenSubordinateExists() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Privilege::new)
                        .set(Privilege::setId, 1L)
                        .set(Privilege::setCode, "sys:privilege")
                        .set(Privilege::setName, "Privileges about privileges")
                        .build()
        );
        when(dao.count(
                PrivilegeQuery.builder()
                        .parentId(1L)
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
