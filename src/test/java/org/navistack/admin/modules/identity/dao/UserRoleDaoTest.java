package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.dtobj.UserRoleDo;
import org.navistack.boot.testsupport.testcontainers.MysqlContainer;
import org.navistack.framework.utils.GenericBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/identity/user_role.sql")
class UserRoleDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private UserRoleDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void selectAllRoleIdsByUserId_shouldWorkAsExpected() {
        assertThat(dao.selectAllRoleIdsByUserId(4L))
                .containsExactlyInAnyOrder(1L, 2L);
        assertThat(dao.selectAllRoleIdsByUserId(100L))
                .isEmpty();
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(UserRoleDo::new).set(UserRoleDo::setId, 1L).set(UserRoleDo::setRoleId, 1L).set(UserRoleDo::setUserId, 1L).build()
                );
    }

    @Test
    void existsByRoleId_shouldWorkAsExpected() {
        assertThat(dao.existsByRoleId(1L)).isTrue();
        assertThat(dao.existsByRoleId(100L)).isFalse();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        UserRoleDo dtObj = GenericBuilder.of(UserRoleDo::new)
                .set(UserRoleDo::setRoleId, 3L)
                .set(UserRoleDo::setUserId, 1L)
                .build();
        assertThat(dao.insert(dtObj)).isEqualTo(1);
        assertThat(dtObj.getId()).isNotNull();
        assertThat(dao.selectById(dtObj.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void insertAll_shouldWorkAsExpected() {
        List<UserRoleDo> dtObjs = Arrays.asList(
                GenericBuilder.of(UserRoleDo::new)
                        .set(UserRoleDo::setRoleId, 3L)
                        .set(UserRoleDo::setUserId, 1L)
                        .build(),
                GenericBuilder.of(UserRoleDo::new)
                        .set(UserRoleDo::setRoleId, 3L)
                        .set(UserRoleDo::setUserId, 2L)
                        .build()
        );
        assertThat(dao.insertAll(dtObjs)).isEqualTo(2);
        for (UserRoleDo dtObj : dtObjs) {
            assertThat(dtObj.getId()).isNotNull();
            assertThat(dao.selectById(dtObj.getId()))
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "roleId", "privilegeId")
                    .isEqualTo(dtObj);
        }
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        UserRoleDo dtObj = GenericBuilder.of(UserRoleDo::new)
                .set(UserRoleDo::setId, 1L)
                .set(UserRoleDo::setRoleId, 3L)
                .set(UserRoleDo::setUserId, 2L)
                .build();
        assertThat(dao.updateById(dtObj)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void deleteById_shouldWorkAsExpected() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectById(2L))
                .isNull();
    }

    @Test
    @Transactional
    void deleteAllByUserId_shouldWorkAsExpected() {
        assertThat(dao.deleteAllByUserId(5L)).isEqualTo(2);
    }
}
