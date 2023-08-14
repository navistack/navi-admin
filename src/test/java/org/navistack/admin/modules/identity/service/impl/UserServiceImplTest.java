package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.UserDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.enums.Gender;
import org.navistack.admin.modules.identity.service.dto.UserDto;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserDao dao;

    @Mock
    private UserRoleDao userRoleDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void create_shouldCreateSuccessfully() {
        when(dao.selectIdByLoginName("cynthia_lyons"))
                .thenReturn(null);
        when(dao.selectIdByMobileNumber("256-344-0699"))
                .thenReturn(null);
        when(dao.selectIdByEmailAddress("cynthia_lyons@dayrep.com"))
                .thenReturn(null);
        UserDto dto = new UserDto();
        dto.setNickName("Cynthia G. Lyons");
        dto.setGender(Gender.FEMALE);
        dto.setBirthday(LocalDate.of(2003, 3, 4));
        dto.setLoginName("cynthia_lyons");
        dto.setMobileNumber("256-344-0699");
        dto.setEmailAddress("cynthia_lyons@dayrep.com");
        service.create(dto);
        verify(dao, times(1))
                .insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenLoginNameDuplicated() {
        when(dao.selectIdByLoginName("cynthia_lyons"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenMobileNumberDuplicated() {
        when(dao.selectIdByLoginName("cynthia_lyons"))
                .thenReturn(null);
        when(dao.selectIdByMobileNumber("256-344-0699"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenEmailAddressDuplicated() {
        when(dao.selectIdByLoginName("cynthia_lyons"))
                .thenReturn(null);
        when(dao.selectIdByMobileNumber("256-344-0699"))
                .thenReturn(null);
        when(dao.selectIdByEmailAddress("cynthia_lyons@dayrep.com"))
                .thenReturn(1L);
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.create(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByLoginName("cynthia_lyons"))
                .thenReturn(null);
        when(dao.selectIdByMobileNumber("256-344-0699"))
                .thenReturn(null);
        when(dao.selectIdByEmailAddress("cynthia_lyons@dayrep.com"))
                .thenReturn(null);
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setNickName("Cynthia G. Lyons");
        dto.setGender(Gender.FEMALE);
        dto.setBirthday(LocalDate.of(2003, 3, 4));
        dto.setLoginName("cynthia_lyons");
        dto.setMobileNumber("256-344-0699");
        dto.setEmailAddress("cynthia_lyons@dayrep.com");
        service.modify(dto);
        verify(dao, times(1))
                .updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenLoginNameDuplicated() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByLoginName("donna_roder"))
                .thenReturn(2L);
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setId(1L);
            dto.setLoginName("donna_roder");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.modify(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenMobileNumberDuplicated() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByLoginName("cynthia_lyons"))
                .thenReturn(null);
        when(dao.selectIdByMobileNumber("415-227-5917"))
                .thenReturn(2L);
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setId(1L);
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("415-227-5917");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.modify(dto);
        })
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenEmailAddressDuplicated() {
        when(dao.existsById(1L))
                .thenReturn(true);
        when(dao.selectIdByLoginName("cynthia_lyons"))
                .thenReturn(null);
        when(dao.selectIdByMobileNumber("256-344-0699"))
                .thenReturn(null);
        when(dao.selectIdByEmailAddress("donna_roder@rhyta.com"))
                .thenReturn(2L);
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setId(1L);
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("donna_roder@rhyta.com");
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
        verify(userRoleDao, times(1))
                .deleteAllByUserId(1L);
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
