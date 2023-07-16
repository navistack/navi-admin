package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.RoleDao;
import org.navistack.admin.modules.identity.dao.RolePrivilegeDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.service.dto.RoleDto;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;

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
        when(dao.selectIdByCode("DEPT_MGR"))
                .thenReturn(null);
        RoleDto dto = new RoleDto();
        dto.setCode("DEPT_MGR");
        dto.setName("Department Manager");
        service.create(dto);
        verify(dao, times(1))
                .insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.selectIdByCode("DEPT_MGR"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            RoleDto dto = new RoleDto();
            dto.setCode("DEPT_MGR");
            dto.setName("Department Manager");
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByCode("DEPT_MGR"))
                .thenReturn(null);
        RoleDto dto = new RoleDto();
        dto.setId(1L);
        dto.setCode("DEPT_MGR");
        dto.setName("Manager of Department");
        service.modify(dto);
        verify(dao, times(1))
                .updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenCodeDuplicated() {
        when(dao.existsById(2L))
                .thenReturn(true);
        when(dao.selectIdByCode("GRL_MGR"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            RoleDto dto = new RoleDto();
            dto.setId(2L);
            dto.setCode("GRL_MGR");
            dto.setName("General Manager");
            service.modify(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(userRoleDao.existsByRoleId(1L))
                .thenReturn(false);
        service.remove(1L);
        verify(dao, times(1))
                .deleteById(any());
        verify(rolePrivilegeDao, times(1))
                .deleteAllByRoleId(1L);
    }

    @Test
    void remove_shouldThrowConstraintViolationExceptionWhenUserExists() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(userRoleDao.existsByRoleId(1L))
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
