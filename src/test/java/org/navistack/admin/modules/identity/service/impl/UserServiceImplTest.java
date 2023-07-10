package org.navistack.admin.modules.identity.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.navistack.admin.modules.identity.dao.UserDao;
import org.navistack.admin.modules.identity.dao.UserRoleDao;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.enums.Gender;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.admin.modules.identity.query.UserRoleQuery;
import org.navistack.admin.modules.identity.service.dto.UserDto;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.utils.GenericBuilder;

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

    @Test
    void create_shouldCreateSuccessfully() {
        UserDto dto = new UserDto();
        dto.setNickName("Cynthia G. Lyons");
        dto.setGender(Gender.FEMALE);
        dto.setBirthday(LocalDate.of(2003, 3, 4));
        dto.setLoginName("cynthia_lyons");
        dto.setMobileNumber("256-344-0699");
        dto.setEmailAddress("cynthia_lyons@dayrep.com");
        service.create(dto);
        verify(dao, times(1)).insert(any());
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenLoginNameDuplicated() {
        when(dao.selectOne(
                UserQuery.builder()
                        .loginName("cynthia_lyons")
                        .build()
        )).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setLoginName, "cynthia_lyons")
                        .build()
        );
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenMobileNumberDuplicated() {
        when(dao.selectOne(
                UserQuery.builder()
                        .mobileNumber("256-344-0699")
                        .build()
        )).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setMobileNumber, "256-344-0699")
                        .build()
        );
        when(dao.selectOne(
                UserQuery.builder()
                        .loginName("cynthia_lyons")
                        .build()
        )).thenReturn(
                null
        );
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void create_shouldThrowDomainValidationExceptionWhenEmailAddressDuplicated() {
        when(dao.selectOne(
                UserQuery.builder()
                        .emailAddress("cynthia_lyons@dayrep.com")
                        .build()
        )).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setEmailAddress, "cynthia_lyons@dayrep.com")
                        .build()
        );
        when(dao.selectOne(
                UserQuery.builder()
                        .loginName("cynthia_lyons")
                        .build()
        )).thenReturn(
                null
        );
        when(dao.selectOne(
                UserQuery.builder()
                        .mobileNumber("256-344-0699")
                        .build()
        )).thenReturn(
                null
        );
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.create(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldModifySuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setNickName, "Cynthia G. Lyons")
                        .set(User::setGender, Gender.FEMALE)
                        .set(User::setBirthday, LocalDate.of(2003, 3, 4))
                        .set(User::setLoginName, "cynthia_lyons")
                        .set(User::setMobileNumber, "256-344-0699")
                        .set(User::setEmailAddress, "cynthia_lyons@dayrep.com")
                        .build()
        );
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setNickName("Cynthia G. Lyons");
        dto.setGender(Gender.FEMALE);
        dto.setBirthday(LocalDate.of(2003, 3, 4));
        dto.setLoginName("cynthia_lyons");
        dto.setMobileNumber("256-344-0699");
        dto.setEmailAddress("cynthia_lyons@dayrep.com");
        service.modify(dto);
        verify(dao, times(1)).updateById(any());
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenLoginNameDuplicated() {
        when(dao.selectOne(
                UserQuery.builder()
                        .loginName("donna_roder")
                        .build()
        )).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 2L)
                        .set(User::setLoginName, "donna_roder")
                        .build()
        );
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setNickName, "Cynthia G. Lyons")
                        .set(User::setGender, Gender.FEMALE)
                        .set(User::setBirthday, LocalDate.of(2003, 3, 4))
                        .set(User::setLoginName, "cynthia_lyons")
                        .set(User::setMobileNumber, "256-344-0699")
                        .set(User::setEmailAddress, "cynthia_lyons@dayrep.com")
                        .build()
        );
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setId(1L);
            dto.setLoginName("donna_roder");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenMobileNumberDuplicated() {
        when(dao.selectOne(
                UserQuery.builder()
                        .mobileNumber("415-227-5917")
                        .build()
        )).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 2L)
                        .set(User::setMobileNumber, "415-227-5917")
                        .build()
        );
        when(dao.selectOne(
                UserQuery.builder()
                        .loginName("cynthia_lyons")
                        .build()
        )).thenReturn(
                null
        );
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setNickName, "Cynthia G. Lyons")
                        .set(User::setGender, Gender.FEMALE)
                        .set(User::setBirthday, LocalDate.of(2003, 3, 4))
                        .set(User::setLoginName, "cynthia_lyons")
                        .set(User::setMobileNumber, "256-344-0699")
                        .set(User::setEmailAddress, "cynthia_lyons@dayrep.com")
                        .build()
        );
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setId(1L);
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("415-227-5917");
            dto.setEmailAddress("cynthia_lyons@dayrep.com");
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void modify_shouldThrowDomainValidationExceptionWhenEmailAddressDuplicated() {
        when(dao.selectOne(
                UserQuery.builder()
                        .emailAddress("donna_roder@rhyta.com")
                        .build()
        )).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 2L)
                        .set(User::setEmailAddress, "donna_roder@rhyta.com")
                        .build()
        );
        when(dao.selectOne(
                UserQuery.builder()
                        .loginName("cynthia_lyons")
                        .build()
        )).thenReturn(
                null
        );
        when(dao.selectOne(
                UserQuery.builder()
                        .mobileNumber("256-344-0699")
                        .build()
        )).thenReturn(
                null
        );
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setNickName, "Cynthia G. Lyons")
                        .set(User::setGender, Gender.FEMALE)
                        .set(User::setBirthday, LocalDate.of(2003, 3, 4))
                        .set(User::setLoginName, "cynthia_lyons")
                        .set(User::setMobileNumber, "256-344-0699")
                        .set(User::setEmailAddress, "cynthia_lyons@dayrep.com")
                        .build()
        );
        assertThatThrownBy(() -> {
            UserDto dto = new UserDto();
            dto.setId(1L);
            dto.setLoginName("cynthia_lyons");
            dto.setMobileNumber("256-344-0699");
            dto.setEmailAddress("donna_roder@rhyta.com");
            service.modify(dto);
        }).isInstanceOf(DomainValidationException.class);
    }

    @Test
    void remove_shouldRemoveSuccessfully() {
        when(dao.selectOneById(1L)).thenReturn(
                GenericBuilder.of(User::new)
                        .set(User::setId, 1L)
                        .set(User::setNickName, "Cynthia G. Lyons")
                        .set(User::setGender, Gender.FEMALE)
                        .set(User::setBirthday, LocalDate.of(2003, 3, 4))
                        .set(User::setLoginName, "cynthia_lyons")
                        .set(User::setMobileNumber, "256-344-0699")
                        .set(User::setEmailAddress, "cynthia_lyons@dayrep.com")
                        .build()
        );
        service.remove(1L);
        verify(dao, times(1)).deleteById(any());
        verify(userRoleDao, times(1)).delete(any(UserRoleQuery.class));
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
