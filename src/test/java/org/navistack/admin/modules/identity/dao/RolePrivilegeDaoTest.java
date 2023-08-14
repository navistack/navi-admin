package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.dtobj.RolePrivilegeDo;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/identity/role_privilege.sql")
class RolePrivilegeDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private RolePrivilegeDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void selectAllByRoleIds_shouldWorkAsExpected() {
        assertThat(dao.selectAllByRoleIds(Collections.singletonList(1L)))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RolePrivilegeDo::new).set(RolePrivilegeDo::setId, 1L).set(RolePrivilegeDo::setRoleId, 1L).set(RolePrivilegeDo::setPrivilegeId, 1L).build(),
                        GenericBuilder.of(RolePrivilegeDo::new).set(RolePrivilegeDo::setId, 2L).set(RolePrivilegeDo::setRoleId, 1L).set(RolePrivilegeDo::setPrivilegeId, 2L).build(),
                        GenericBuilder.of(RolePrivilegeDo::new).set(RolePrivilegeDo::setId, 3L).set(RolePrivilegeDo::setRoleId, 1L).set(RolePrivilegeDo::setPrivilegeId, 3L).build(),
                        GenericBuilder.of(RolePrivilegeDo::new).set(RolePrivilegeDo::setId, 4L).set(RolePrivilegeDo::setRoleId, 1L).set(RolePrivilegeDo::setPrivilegeId, 4L).build(),
                        GenericBuilder.of(RolePrivilegeDo::new).set(RolePrivilegeDo::setId, 5L).set(RolePrivilegeDo::setRoleId, 1L).set(RolePrivilegeDo::setPrivilegeId, 5L).build()
                );
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(RolePrivilegeDo::new).set(RolePrivilegeDo::setId, 1L).set(RolePrivilegeDo::setRoleId, 1L).set(RolePrivilegeDo::setPrivilegeId, 1L).build()
                );
    }

    @Test
    void selectAllPrivilegeIdsByRoleId_shouldWorkAsExpected() {
        assertThat(dao.selectAllPrivilegeIdsByRoleId(1L))
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L);
        assertThat(dao.selectAllPrivilegeIdsByRoleId(100L))
                .isEmpty();
    }

    @Test
    void selectAllPrivilegeIdsByRoleIds_shouldWorkAsExpected() {
        assertThat(dao.selectAllPrivilegeIdsByRoleIds(Arrays.asList(1L, 2L)))
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 4L, 5L, 6L, 7L, 8L);
        assertThat(dao.selectAllPrivilegeIdsByRoleIds(Arrays.asList(100L, 200L)))
                .isEmpty();
        assertThat(dao.selectAllPrivilegeIdsByRoleIds(Collections.emptyList()))
                .isEmpty();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        RolePrivilegeDo dtObj = GenericBuilder.of(RolePrivilegeDo::new)
                .set(RolePrivilegeDo::setRoleId, 3L)
                .set(RolePrivilegeDo::setPrivilegeId, 1L)
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
        List<RolePrivilegeDo> dtObjs = Arrays.asList(
                GenericBuilder.of(RolePrivilegeDo::new)
                        .set(RolePrivilegeDo::setRoleId, 3L)
                        .set(RolePrivilegeDo::setPrivilegeId, 1L)
                        .build(),
                GenericBuilder.of(RolePrivilegeDo::new)
                        .set(RolePrivilegeDo::setRoleId, 3L)
                        .set(RolePrivilegeDo::setPrivilegeId, 2L)
                        .build()
        );
        assertThat(dao.insertAll(dtObjs)).isEqualTo(2);
        for (RolePrivilegeDo dtObj : dtObjs) {
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
        RolePrivilegeDo dtObj = GenericBuilder.of(RolePrivilegeDo::new)
                .set(RolePrivilegeDo::setId, 1L)
                .set(RolePrivilegeDo::setRoleId, 3L)
                .set(RolePrivilegeDo::setPrivilegeId, 2L)
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
    void deleteAllByRoleId_shouldWorkAsExpected() {
        assertThat(dao.deleteAllByRoleId(1L)).isEqualTo(5);
    }
}
