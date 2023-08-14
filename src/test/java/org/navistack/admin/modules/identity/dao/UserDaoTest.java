package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.identity.enums.Gender;
import org.navistack.admin.modules.identity.enums.UserStatus;
import org.navistack.admin.modules.identity.query.UserLoginNameQuery;
import org.navistack.admin.modules.identity.query.UserQuery;
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
@Sql(scripts = "classpath:data/identity/user.sql")
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
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(UserQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(UserQuery.builder().nickName("Test User 01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(UserQuery.builder().loginName("testuser01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(UserQuery.builder().mobileNumber("813-862-8139").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(UserQuery.builder().emailAddress("reilly.durgan49@yahoo.com").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(UserQuery.builder().status(UserStatus.FORBIDDEN).build())).isEqualTo(5L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        UserQuery query = UserQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest)).hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserDo::new).set(UserDo::setId, 6L).set(UserDo::setNickName, "Test User 06").set(UserDo::setAvatarUrl, "avatar:/1b/23a4eacf6463571edc6d1b28209db6/avatar.jpg").set(UserDo::setGender, Gender.FEMALE).set(UserDo::setBirthday, LocalDate.of(1998, 5, 24)).set(UserDo::setLoginName, "testuser06").set(UserDo::setMobileNumber, "212-403-5257").set(UserDo::setEmailAddress, "maye6@gmail.com").set(UserDo::setPassword, "$2a$10$MKDPkDwWzgDluid0KHAGBuxCPHxyjiM4jf.yg7sxfbH2gLtyx.IB6").set(UserDo::setStatus, UserStatus.NORMAL).build(),
                        GenericBuilder.of(UserDo::new).set(UserDo::setId, 7L).set(UserDo::setNickName, "Test User 07").set(UserDo::setAvatarUrl, "avatar:/d7/0be96ca3a480e1166ddae4e6cc66bc/avatar.jpg").set(UserDo::setGender, Gender.FEMALE).set(UserDo::setBirthday, LocalDate.of(2006, 1, 12)).set(UserDo::setLoginName, "testuser07").set(UserDo::setMobileNumber, "585-548-8890").set(UserDo::setEmailAddress, "lina.hauck90@yahoo.com").set(UserDo::setPassword, "$2a$10$9SeFrv7eVWCDSJro2b4HLOlld8FkhptLqypWLeB87p4EKBVpUdxbu").set(UserDo::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(UserDo::new).set(UserDo::setId, 8L).set(UserDo::setNickName, "Test User 08").set(UserDo::setAvatarUrl, "avatar:/1d/bf08938bec80385904a95d14e7d8b0/avatar.jpg").set(UserDo::setGender, Gender.MALE).set(UserDo::setBirthday, LocalDate.of(2007, 1, 15)).set(UserDo::setLoginName, "testuser08").set(UserDo::setMobileNumber, "347-750-3852").set(UserDo::setEmailAddress, "bertrand68@hotmail.com").set(UserDo::setPassword, "$2a$10$4RFYvL7ASgToD.Cl73/N3eBv7RiA3EPn1FOb7C3teP4E6EtD8bIi2").set(UserDo::setStatus, UserStatus.FORBIDDEN).build(),
                        GenericBuilder.of(UserDo::new).set(UserDo::setId, 9L).set(UserDo::setNickName, "Test User 09").set(UserDo::setAvatarUrl, "avatar:/fc/7a13533dcb0cc3273254cc26c3fbf8/avatar.jpg").set(UserDo::setGender, Gender.MALE).set(UserDo::setBirthday, LocalDate.of(2008, 3, 3)).set(UserDo::setLoginName, "testuser09").set(UserDo::setMobileNumber, "810-937-0914").set(UserDo::setEmailAddress, "kellen69@hotmail.com").set(UserDo::setPassword, "$2a$10$SAGMXvD/ilDG6tVvygh2P.Sjvvk6V2SfRF9UPxKjbmXB4SNi7lnqS").set(UserDo::setStatus, UserStatus.NORMAL).build(),
                        GenericBuilder.of(UserDo::new).set(UserDo::setId, 10L).set(UserDo::setNickName, "Test User 10").set(UserDo::setAvatarUrl, "avatar:/cb/07487428c4d3e5b29bbd539759e3b4/avatar.jpg").set(UserDo::setGender, Gender.FEMALE).set(UserDo::setBirthday, LocalDate.of(2018, 3, 5)).set(UserDo::setLoginName, "testuser10").set(UserDo::setMobileNumber, "304-367-6149").set(UserDo::setEmailAddress, "kris.schamberger@hotmail.com").set(UserDo::setPassword, "$2a$10$/1dRAuRiC7Hspwgx2QwqyO93xVWp4ewir1gREsKTH8Rl.nyFWwlpG").set(UserDo::setStatus, UserStatus.NORMAL).build()).isSortedAccordingTo(Comparator.comparingLong(UserDo::getId).reversed()
                );
    }

    @Test
    void selectByLoginName_shouldWorkAsExpected() {
        UserLoginNameQuery query = UserLoginNameQuery.builder()
                .loginName("testuser01")
                .mobileNumber("813-862-8139")
                .emailAddress("reilly.durgan49@yahoo.com")
                .build();
        assertThat(dao.selectByLoginName(query)).usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status").isEqualTo(GenericBuilder.of(UserDo::new).set(UserDo::setId, 1L).set(UserDo::setNickName, "Test User 01").set(UserDo::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(UserDo::setGender, Gender.FEMALE).set(UserDo::setBirthday, LocalDate.of(1975, 2, 15)).set(UserDo::setLoginName, "testuser01").set(UserDo::setMobileNumber, "813-862-8139").set(UserDo::setEmailAddress, "reilly.durgan49@yahoo.com").set(UserDo::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(UserDo::setStatus, UserStatus.FORBIDDEN).build());
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(
                        GenericBuilder.of(UserDo::new).set(UserDo::setId, 1L).set(UserDo::setNickName, "Test User 01").set(UserDo::setAvatarUrl, "avatar:/bb/103d5f3fbd2f5a98041dc919752a05/avatar.jpg").set(UserDo::setGender, Gender.FEMALE).set(UserDo::setBirthday, LocalDate.of(1975, 2, 15)).set(UserDo::setLoginName, "testuser01").set(UserDo::setMobileNumber, "813-862-8139").set(UserDo::setEmailAddress, "reilly.durgan49@yahoo.com").set(UserDo::setPassword, "$2a$10$VexWghTW1CbMOR5dmDxNLeDWhjOQCRywkpHcR9cuDWEMDmCelplpy").set(UserDo::setStatus, UserStatus.FORBIDDEN).build()
                );
    }

    @Test
    void selectIdByLoginName_shouldWorkAsExpected() {
        assertThat(dao.selectIdByLoginName("testuser01")).isEqualTo(1L);
        assertThat(dao.selectIdByLoginName("testuser100")).isNull();
    }

    @Test
    void selectIdByMobileNumber_shouldWorkAsExpected() {
        assertThat(dao.selectIdByMobileNumber("813-862-8139")).isEqualTo(1L);
        assertThat(dao.selectIdByMobileNumber("440-832-6332")).isNull();
    }

    @Test
    void selectIdByEmailAddress_shouldWorkAsExpected() {
        assertThat(dao.selectIdByEmailAddress("reilly.durgan49@yahoo.com")).isEqualTo(1L);
        assertThat(dao.selectIdByEmailAddress("TeraEMuro@rhyta.com")).isNull();
    }

    @Test
    void existsById_shouldWorkAsExpected() {
        assertThat(dao.existsById(1L)).isTrue();
        assertThat(dao.existsById(100L)).isFalse();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        UserDo dtObj = GenericBuilder.of(UserDo::new)
                .set(UserDo::setNickName, "Test User 11")
                .set(UserDo::setAvatarUrl, "avatar:/87/87c4622c26edaacf3e076241d50d119a/avatar.jpg")
                .set(UserDo::setGender, Gender.MALE)
                .set(UserDo::setBirthday, LocalDate.of(1981, 7, 8))
                .set(UserDo::setLoginName, "testuser11")
                .set(UserDo::setMobileNumber, "541-466-0955")
                .set(UserDo::setEmailAddress, "justine39@hotmail.com")
                .set(UserDo::setPassword, "$2a$10$ci.q7z.crGh.B16jmNWn5eeU5jb61FpwlpGgOVDIX1XPNQPRzqWHq")
                .set(UserDo::setStatus, UserStatus.FORBIDDEN)
                .build();
        assertThat(dao.insert(dtObj)).isEqualTo(1);
        assertThat(dtObj.getId()).isNotNull();
        assertThat(dao.selectById(dtObj.getId()))
                .usingRecursiveComparison().comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        UserDo dtObj = GenericBuilder.of(UserDo::new)
                .set(UserDo::setId, 1L)
                .set(UserDo::setNickName, "Test User 12")
                .set(UserDo::setAvatarUrl, "avatar:/80/80199499de32ecbeb2399ddba248558d/avatar.jpg")
                .set(UserDo::setGender, Gender.MALE)
                .set(UserDo::setBirthday, LocalDate.of(1983, 5, 8))
                .set(UserDo::setLoginName, "testuser12")
                .set(UserDo::setMobileNumber, "774-307-3114")
                .set(UserDo::setEmailAddress, "nolan_nolan26@gmail.com")
                .set(UserDo::setPassword, "$2a$10$m6HXHOQlwuokqGVwZU//Ye2RZim8R.4vXPj4RMw/vXdKb9axQjOci")
                .set(UserDo::setStatus, UserStatus.NORMAL)
                .build();
        assertThat(dao.updateById(dtObj)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "nick_name", "avatar_url", "gender", "birthday", "login_name", "mobile_number", "email_address", "password", "status")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void deleteById_shouldWorkAsExpected() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectById(2L)).isNull();
    }
}
