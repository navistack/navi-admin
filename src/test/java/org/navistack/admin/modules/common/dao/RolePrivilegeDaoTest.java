package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.RolePrivilege;
import org.navistack.admin.modules.common.query.RolePrivilegeQuery;
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

import java.util.Collections;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/role_privilege.sql")
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
    void testSelect() {
        assertThat(dao.select(RolePrivilegeQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactly(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
        assertThat(dao.select(RolePrivilegeQuery.builder().roleId(1L).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 2L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 2L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 3L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 3L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 4L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 4L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 5L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 5L).build()
                );
        assertThat(dao.select(RolePrivilegeQuery.builder().privilegeId(4L).build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 4L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 4L).build(),
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 6L).set(RolePrivilege::setRoleId, 2L).set(RolePrivilege::setPrivilegeId, 4L).build()
                );
    }

    @Test
    void testSelectByRoleIdIn() {
        assertThat(dao.selectByRoleIdIn(Collections.singletonList(1L)))
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
    void testCount() {
        assertThat(dao.count(RolePrivilegeQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(RolePrivilegeQuery.builder().roleId(1L).build())).isEqualTo(5L);
        assertThat(dao.count(RolePrivilegeQuery.builder().privilegeId(4L).build())).isEqualTo(2L);
    }

    @Test
    void testSelectWithPageable() {
        RolePrivilegeQuery query = RolePrivilegeQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
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
    void testSelectOne() {
        assertThat(dao.selectOne(RolePrivilegeQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
        assertThat(dao.selectOne(RolePrivilegeQuery.builder().roleId(1L).privilegeId(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(RolePrivilege::new).set(RolePrivilege::setId, 1L).set(RolePrivilege::setRoleId, 1L).set(RolePrivilege::setPrivilegeId, 1L).build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        RolePrivilege entity = GenericBuilder.of(RolePrivilege::new)
                .set(RolePrivilege::setId, 11L)
                .set(RolePrivilege::setRoleId, 3L)
                .set(RolePrivilege::setPrivilegeId, 1L)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        RolePrivilege entity = GenericBuilder.of(RolePrivilege::new)
                .set(RolePrivilege::setId, 1L)
                .set(RolePrivilege::setRoleId, 3L)
                .set(RolePrivilege::setPrivilegeId, 2L)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testDeleteById() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectOneById(2L))
                .isNull();
    }

    @Test
    void testDelete() {
        assertThat(dao.delete(RolePrivilegeQuery.builder().roleId(1L).build())).isEqualTo(5);
        assertThat(dao.delete(RolePrivilegeQuery.builder().privilegeId(6L).build())).isEqualTo(1);
    }
}
