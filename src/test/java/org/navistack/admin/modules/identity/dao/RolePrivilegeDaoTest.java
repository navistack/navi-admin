package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.entity.RolePrivilege;
import org.navistack.admin.modules.identity.query.RolePrivilegeQuery;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    void selectAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQuery(RolePrivilegeQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactly(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
        assertThat(dao.selectAllByQuery(RolePrivilegeQuery.builder().roleId(1L).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 2L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 2L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 3L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 3L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 4L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 4L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 5L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 5L).build()
                );
        assertThat(dao.selectAllByQuery(RolePrivilegeQuery.builder().privilegeId(4L).build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 4L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 4L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 6L).set(RolePrivilege::setRoleId, 2L).set(RolePrivilege::setPrivilegeId, 4L).build()
                );
    }

    @Test
    void selectAllByRoleIds_shouldWorkAsExpected() {
        assertThat(dao.selectAllByRoleIds(Collections.singletonList(1L)))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 2L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 2L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 3L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 3L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 4L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 4L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 5L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 5L).build()
                );
    }

    @Test
    void existsByQuery_shouldWorkAsExpected() {
        assertThat(dao.existsByQuery(RolePrivilegeQuery.builder().id(1L).build())).isTrue();
        assertThat(dao.existsByQuery(RolePrivilegeQuery.builder().roleId(1L).build())).isTrue();
        assertThat(dao.existsByQuery(RolePrivilegeQuery.builder().privilegeId(4L).build())).isTrue();

        assertThat(dao.existsByQuery(RolePrivilegeQuery.builder().id(100L).build())).isFalse();
        assertThat(dao.existsByQuery(RolePrivilegeQuery.builder().roleId(100L).build())).isFalse();
        assertThat(dao.existsByQuery(RolePrivilegeQuery.builder().privilegeId(100L).build())).isFalse();
    }

    @Test
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(RolePrivilegeQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(RolePrivilegeQuery.builder().roleId(1L).build())).isEqualTo(5L);
        assertThat(dao.countByQuery(RolePrivilegeQuery.builder().privilegeId(4L).build())).isEqualTo(2L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        RolePrivilegeQuery query = RolePrivilegeQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 6L).set(RolePrivilege::setRoleId, 2L).set(RolePrivilege::setPrivilegeId, 4L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 7L).set(RolePrivilege::setRoleId, 2L).set(RolePrivilege::setPrivilegeId, 5L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 8L).set(RolePrivilege::setRoleId, 2L).set(RolePrivilege::setPrivilegeId, 6L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 9L).set(RolePrivilege::setRoleId, 2L).set(RolePrivilege::setPrivilegeId, 7L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 10L).set(RolePrivilege::setRoleId, 2L).set(RolePrivilege::setPrivilegeId, 8L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(RolePrivilege::getId).reversed());
    }

    @Test
    void selectByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectByQuery(RolePrivilegeQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
        assertThat(dao.selectByQuery(RolePrivilegeQuery.builder().roleId(1L).privilegeId(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        RolePrivilege entity = GenericBuilder.of(RolePrivilege::new)
                .set(RolePrivilege::setId, 11L)
                .set(RolePrivilege::setRoleId, 3L)
                .set(RolePrivilege::setPrivilegeId, 1L)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void insertAll_shouldWorkAsExpected() {
        List<RolePrivilege> entities = Arrays.asList(
                GenericBuilder.of(RolePrivilege::new)
                        .set(RolePrivilege::setId, 11L)
                        .set(RolePrivilege::setRoleId, 3L)
                        .set(RolePrivilege::setPrivilegeId, 1L)
                        .build(),
                GenericBuilder.of(RolePrivilege::new)
                        .set(RolePrivilege::setId, 12L)
                        .set(RolePrivilege::setRoleId, 3L)
                        .set(RolePrivilege::setPrivilegeId, 2L)
                        .build()
        );
        assertThat(dao.insertAll(entities)).isEqualTo(2);
        for (RolePrivilege entity : entities) {
            assertThat(dao.selectById(entity.getId()))
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "roleId", "privilegeId")
                    .isEqualTo(entity);
        }
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        RolePrivilege entity = GenericBuilder.of(RolePrivilege::new)
                .set(RolePrivilege::setId, 1L)
                .set(RolePrivilege::setRoleId, 3L)
                .set(RolePrivilege::setPrivilegeId, 2L)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(entity);
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
    void deleteAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.deleteAllByQuery(RolePrivilegeQuery.builder().roleId(1L).build())).isEqualTo(5);
        assertThat(dao.deleteAllByQuery(RolePrivilegeQuery.builder().privilegeId(6L).build())).isEqualTo(1);
    }

    @Test
    @Transactional
    void deleteAllByRoleId_shouldWorkAsExpected() {
        assertThat(dao.deleteAllByRoleId(1L)).isEqualTo(5);
    }
}
