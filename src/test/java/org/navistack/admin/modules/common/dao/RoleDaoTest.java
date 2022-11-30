package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.common.query.RoleQuery;
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

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/role.sql")
class RoleDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private RoleDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void testSelect() {
        assertThat(dao.select(RoleQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Role::new).set(Role::setId, 1L).set(Role::setCode, "ROLE_CODE_01").set(Role::setName, "ROLE NAME 01").build()
                );
        assertThat(dao.select(RoleQuery.builder().code("ROLE_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Role::new).set(Role::setId, 1L).set(Role::setCode, "ROLE_CODE_01").set(Role::setName, "ROLE NAME 01").build()
                );
        assertThat(dao.select(RoleQuery.builder().name("ROLE NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Role::new).set(Role::setId, 1L).set(Role::setCode, "ROLE_CODE_01").set(Role::setName, "ROLE NAME 01").build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(RoleQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(RoleQuery.builder().code("ROLE_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.count(RoleQuery.builder().name("ROLE NAME 01").build())).isEqualTo(1L);
    }

    @Test
    void testSelectWithPageable() {
        RoleQuery query = RoleQuery.builder()
                .name("ROLE NAME")
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(Role::new).set(Role::setId, 6L).set(Role::setCode, "ROLE_CODE_06").set(Role::setName, "ROLE NAME 06").build(),
                        GenericBuilder.of(Role::new).set(Role::setId, 7L).set(Role::setCode, "ROLE_CODE_07").set(Role::setName, "ROLE NAME 07").build(),
                        GenericBuilder.of(Role::new).set(Role::setId, 8L).set(Role::setCode, "ROLE_CODE_08").set(Role::setName, "ROLE NAME 08").build(),
                        GenericBuilder.of(Role::new).set(Role::setId, 9L).set(Role::setCode, "ROLE_CODE_09").set(Role::setName, "ROLE NAME 09").build(),
                        GenericBuilder.of(Role::new).set(Role::setId, 10L).set(Role::setCode, "ROLE_CODE_10").set(Role::setName, "ROLE NAME 10").build()
                ).isSortedAccordingTo(Comparator.comparingLong(Role::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(RoleQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Role::new).set(Role::setId, 1L).set(Role::setCode, "ROLE_CODE_01").set(Role::setName, "ROLE NAME 01").build()
                );
        assertThat(dao.selectOne(RoleQuery.builder().code("ROLE_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Role::new).set(Role::setId, 1L).set(Role::setCode, "ROLE_CODE_01").set(Role::setName, "ROLE NAME 01").build()
                );
        assertThat(dao.selectOne(RoleQuery.builder().name("ROLE NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Role::new).set(Role::setId, 1L).set(Role::setCode, "ROLE_CODE_01").set(Role::setName, "ROLE NAME 01").build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Role::new).set(Role::setId, 1L).set(Role::setCode, "ROLE_CODE_01").set(Role::setName, "ROLE NAME 01").build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        Role entity = GenericBuilder.of(Role::new)
                .set(Role::setId, 11L)
                .set(Role::setCode, "ROLE_CODE_11")
                .set(Role::setName, "ROLE NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        Role entity = GenericBuilder.of(Role::new)
                .set(Role::setId, 1L)
                .set(Role::setCode, "ROLE_CODE_12")
                .set(Role::setName, "ROLE NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testDeleteById() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectOneById(2L))
                .isNull();
    }
}
