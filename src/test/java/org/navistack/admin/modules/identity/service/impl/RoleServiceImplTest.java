package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.RoleDao;
import org.navistack.admin.modules.identity.dao.RolePrivilegeDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.entity.Role;
import org.navistack.admin.modules.identity.query.RolePrivilegeQuery;
import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.admin.modules.identity.query.UserRoleQuery;
import org.navistack.admin.modules.identity.service.dto.RoleDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.utils.GenericBuilder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl service;

    @Mock
    private RoleDao dao;

    @Mock
    private RolePrivilegeDao rolePrivilegeDao;

    @Mock
    private UserRoleDao userRoleDao;


    @Test
    void create_shouldCreateSuccessfully() {
        RoleDto dto = new RoleDto();
        dto.setCode("DEPT_MGR");
        dto.setName("Department Manager");
        service.create(dto);
        verify(dao, times(1)).insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                RoleQuery.builder()
                        .code("DEPT_MGR")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Role::new)
                        .set(Role::setId, 1L)
                        .set(Role::setCode, "DEPT_MGR")
                        .set(Role::setName, "Department Manager")
                        .build()
        );
        assertThatThrownBy(() -> {
            RoleDto dto = new RoleDto();
            dto.setCode("DEPT_MGR");
            dto.setName("Department Manager");
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Role::new)
                        .set(Role::setId, 1L)
                        .set(Role::setCode, "DEPT_MGR")
                        .set(Role::setName, "Department Manager")
                        .build()
        );
        RoleDto dto = new RoleDto();
        dto.setId(1L);
        dto.setCode("DEPT_MGR");
        dto.setName("Manager of Department");
        service.modify(dto);
        verify(dao, times(1)).updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectOne(
                RoleQuery.builder()
                        .code("GRL_MGR")
                        .build()
        )).thenReturn(
                GenericBuilder.of(Role::new)
                        .set(Role::setId, 1L)
                        .set(Role::setCode, "GRL_MGR")
                        .set(Role::setName, "General Manager")
                        .build()
        );
        when(dao.selectOneById(2L)).thenReturn(
                GenericBuilder.of(Role::new)
                        .set(Role::setId, 2L)
                        .set(Role::setCode, "DEPT_MGR")
                        .set(Role::setName, "Department Manager")
                        .build()
        );
        assertThatThrownBy(() -> {
            RoleDto dto = new RoleDto();
            dto.setId(2L);
            dto.setCode("GRL_MGR");
            dto.setName("General Manager");
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Role::new)
                        .set(Role::setId, 1L)
                        .set(Role::setCode, "DEPT_MGR")
                        .set(Role::setName, "Department Manager")
                        .build()
        );
        service.remove(1L);
        verify(dao, times(1)).deleteById(any());
        verify(rolePrivilegeDao, times(1)).delete(any(RolePrivilegeQuery.class));
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenUserExists() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(Role::new)
                        .set(Role::setId, 1L)
                        .set(Role::setCode, "DEPT_MGR")
                        .set(Role::setName, "Department Manager")
                        .build()
        );
        when(userRoleDao.count(
                UserRoleQuery.builder()
                        .roleId(1L)
                        .build()
        )).thenReturn(10L);
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
