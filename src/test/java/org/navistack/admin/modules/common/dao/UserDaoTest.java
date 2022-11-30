package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.common.enums.Gender;
import org.navistack.admin.modules.common.enums.UserStatus;
import org.navistack.admin.modules.common.query.UserLoginNameQuery;
import org.navistack.admin.modules.common.query.UserQuery;
import org.navistack.boot.testsupport.testcontainers.MysqlContainer;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.data.Sort;
import org.navistack.framework.utils.GenericBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/user.sql")
class UserDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private UserDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void testSelect() {
        assertThat(dao.select(UserQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactly(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.select(UserQuery.builder().nickName("Test User 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactly(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.select(UserQuery.builder().loginName("testuser01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactly(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.select(UserQuery.builder().mobileNumber("813-862-8139").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactly(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.select(UserQuery.builder().emailAddress("reilly.durgan49@yahoo.com").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactly(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.select(UserQuery.builder().status(UserStatus.FORBIDDEN).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(User::new).set(User::setId, 2L).set(User::setNickName, "Test User 02").set(User::setAvatarUrl, "avatar:/79/b3d491b73cdbe93d07225ceede61cb/avatar.jpg").set(User::setGender, Gender.MALE).set(User::setBirthday, LocalDate.of(1980, 12, 23)).set(User::setLoginName, "testuser02").set(User::setMobileNumber, "214-597-4799").set(User::setEmailAddress, "brisa20@hotmail.com").set(User::setPassword, "$2a$10$leOewqDhGCBqh71AlThF0.YJb2daj8pI1lanu.DWb7TKv8MN24OZq").set(User::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(User::new).set(User::setId, 5L).set(User::setNickName, "Test User 05").set(User::setAvatarUrl, "avatar:/0e/dbba2992da4aba7c3ec8836ee9f140/avatar.jpg").set(User::setGender, Gender.MALE).set(User::setBirthday, LocalDate.of(1996, 8, 23)).set(User::setLoginName, "testuser05").set(User::setMobileNumber, "360-523-0071").set(User::setEmailAddress, "mariane29@hotmail.com").set(User::setPassword, "$2a$10$v.xQu/wxajge5SW0eH.fuedSmsj7G1XkyNiIz84wEXUlqJZkSalxG").set(User::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(User::new).set(User::setId, 7L).set(User::setNickName, "Test User 07").set(User::setAvatarUrl, "avatar:/d7/0be96ca3a480e1166ddae4e6cc66bc/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(2006, 1, 12)).set(User::setLoginName, "testuser07").set(User::setMobileNumber, "585-548-8890").set(User::setEmailAddress, "lina.hauck90@yahoo.com").set(User::setPassword, "$2a$10$9SeFrv7eVWCDSJro2b4HLOlld8FkhptLqypWLeB87p4EKBVpUdxbu").set(User::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(User::new).set(User::setId, 8L).set(User::setNickName, "Test User 08").set(User::setAvatarUrl, "avatar:/1d/bf08938bec80385904a95d14e7d8b0/avatar.jpg").set(User::setGender, Gender.MALE).set(User::setBirthday, LocalDate.of(2007, 1, 15)).set(User::setLoginName, "testuser08").set(User::setMobileNumber, "347-750-3852").set(User::setEmailAddress, "bertrand68@hotmail.com").set(User::setPassword, "$2a$10$4RFYvL7ASgToD.Cl73/N3eBv7RiA3EPn1FOb7C3teP4E6EtD8bIi2").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(UserQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(UserQuery.builder().nickName("Test User 01").build())).isEqualTo(1L);
        assertThat(dao.count(UserQuery.builder().loginName("testuser01").build())).isEqualTo(1L);
        assertThat(dao.count(UserQuery.builder().mobileNumber("813-862-8139").build())).isEqualTo(1L);
        assertThat(dao.count(UserQuery.builder().emailAddress("reilly.durgan49@yahoo.com").build())).isEqualTo(1L);
        assertThat(dao.count(UserQuery.builder().status(UserStatus.FORBIDDEN).build())).isEqualTo(5L);
    }

    @Test
    void testSelectWithPageable() {
        UserQuery query = UserQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest)).hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(User::new).set(User::setId, 6L).set(User::setNickName, "Test User 06").set(User::setAvatarUrl, "avatar:/1b/23a4eacf6463571edc6d1b28209db6/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1998, 5, 24)).set(User::setLoginName, "testuser06").set(User::setMobileNumber, "212-403-5257").set(User::setEmailAddress, "maye6@gmail.com").set(User::setPassword, "$2a$10$MKDPkDwWzgDluid0KHAGBuxCPHxyjiM4jf.yg7sxfbH2gLtyx.IB6").set(User::setStatus, UserStatus.NORMAL).build(),
                        GenericBuilder.of(User::new).set(User::setId, 7L).set(User::setNickName, "Test User 07").set(User::setAvatarUrl, "avatar:/d7/0be96ca3a480e1166ddae4e6cc66bc/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(2006, 1, 12)).set(User::setLoginName, "testuser07").set(User::setMobileNumber, "585-548-8890").set(User::setEmailAddress, "lina.hauck90@yahoo.com").set(User::setPassword, "$2a$10$9SeFrv7eVWCDSJro2b4HLOlld8FkhptLqypWLeB87p4EKBVpUdxbu").set(User::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(User::new).set(User::setId, 8L).set(User::setNickName, "Test User 08").set(User::setAvatarUrl, "avatar:/1d/bf08938bec80385904a95d14e7d8b0/avatar.jpg").set(User::setGender, Gender.MALE).set(User::setBirthday, LocalDate.of(2007, 1, 15)).set(User::setLoginName, "testuser08").set(User::setMobileNumber, "347-750-3852").set(User::setEmailAddress, "bertrand68@hotmail.com").set(User::setPassword, "$2a$10$4RFYvL7ASgToD.Cl73/N3eBv7RiA3EPn1FOb7C3teP4E6EtD8bIi2").set(User::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(User::new).set(User::setId, 9L).set(User::setNickName, "Test User 09").set(User::setAvatarUrl, "avatar:/fc/7a13533dcb0cc3273254cc26c3fbf8/avatar.jpg").set(User::setGender, Gender.MALE).set(User::setBirthday, LocalDate.of(2008, 3, 3)).set(User::setLoginName, "testuser09").set(User::setMobileNumber, "810-937-0914").set(User::setEmailAddress, "kellen69@hotmail.com").set(User::setPassword, "$2a$10$SAGMXvD/ilDG6tVvygh2P.Sjvvk6V2SfRF9UPxKjbmXB4SNi7lnqS").set(User::setStatus, UserStatus.NORMAL).build(),
                        GenericBuilder.of(User::new).set(User::setId, 10L).set(User::setNickName, "Test User 10").set(User::setAvatarUrl, "avatar:/cb/07487428c4d3e5b29bbd539759e3b4/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(2018, 3, 5)).set(User::setLoginName, "testuser10").set(User::setMobileNumber, "304-367-6149").set(User::setEmailAddress, "kris.schamberger@hotmail.com").set(User::setPassword, "$2a$10$/1dRAuRiC7Hspwgx2QwqyO93xVWp4ewir1gREsKTH8Rl.nyFWwlpG").set(User::setStatus, UserStatus.NORMAL).build()).isSortedAccordingTo(Comparator.comparingLong(User::getId).reversed()
                );
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(UserQuery.builder().id(1L).build()))
                .usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.selectOne(UserQuery.builder().loginName("testuser01").build()))
                .usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.selectOne(UserQuery.builder().mobileNumber("813-862-8139").build()))
                .usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
        assertThat(dao.selectOne(UserQuery.builder().emailAddress("reilly.durgan49@yahoo.com").build()))
                .usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
    }

    @Test
    void testSelectOneByLoginName() {
        UserLoginNameQuery query = UserLoginNameQuery.builder()
                .loginName("testuser01")
                .mobileNumber("813-862-8139")
                .emailAddress("reilly.durgan49@yahoo.com")
                .build();
        assertThat(dao.selectOneByLoginName(query)).usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status").isEqualTo(GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build());
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(
                        GenericBuilder.of(User::new).set(User::setId, 1L).set(User::setNickName, "Test User 01").set(User::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(User::setGender, Gender.FEMALE).set(User::setBirthday, LocalDate.of(1975, 2, 15)).set(User::setLoginName, "testuser01").set(User::setMobileNumber, "813-862-8139").set(User::setEmailAddress, "reilly.durgan49@yahoo.com").set(User::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(User::setStatus, UserStatus.FORBIDDEN).build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        User entity = GenericBuilder.of(User::new)
                .set(User::setId, 11L)
                .set(User::setNickName, "Test User 11")
                .set(User::setAvatarUrl, "avatar:/87/87c4622c26edaacf3e076241d50d119a/avatar.jpg")
                .set(User::setGender, Gender.MALE)
                .set(User::setBirthday, LocalDate.of(1981, 7, 8))
                .set(User::setLoginName, "testuser11")
                .set(User::setMobileNumber, "541-466-0955")
                .set(User::setEmailAddress, "justine39@hotmail.com")
                .set(User::setPassword, "$2a$10$ci.q7z.crGh.B16jmNWn5eeU5jb61FpwlpGgOVDIX1XPNQPRzqWHq")
                .set(User::setStatus, UserStatus.FORBIDDEN)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        User entity = GenericBuilder.of(User::new)
                .set(User::setId, 1L)
                .set(User::setNickName, "Test User 12")
                .set(User::setAvatarUrl, "avatar:/80/80199499de32ecbeb2399ddba248558d/avatar.jpg")
                .set(User::setGender, Gender.MALE)
                .set(User::setBirthday, LocalDate.of(1983, 5, 8))
                .set(User::setLoginName, "testuser12")
                .set(User::setMobileNumber, "774-307-3114")
                .set(User::setEmailAddress, "nolan_nolan26@gmail.com")
                .set(User::setPassword, "$2a$10$m6HXHOQlwuokqGVwZU//Ye2RZim8R.4vXPj4RMw/vXdKb9axQjOci")
                .set(User::setStatus, UserStatus.NORMAL)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testDeleteById() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectOneById(2L)).isNull();
    }
}
