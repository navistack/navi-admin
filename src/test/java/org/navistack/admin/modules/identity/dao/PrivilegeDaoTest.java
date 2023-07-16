package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
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
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/identity/privilege.sql")
class PrivilegeDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private PrivilegeDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void selectAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQuery(PrivilegeQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(PrivilegeQuery.builder().code("PRIVILEGE_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(PrivilegeQuery.builder().name("PRIVILEGE NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(PrivilegeQuery.builder().parentId(1L).build()))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 2L).set(Privilege::setCode, "PRIVILEGE_CODE_02").set(Privilege::setName, "PRIVILEGE NAME 02").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 3L).set(Privilege::setCode, "PRIVILEGE_CODE_03").set(Privilege::setName, "PRIVILEGE NAME 03").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 4L).set(Privilege::setCode, "PRIVILEGE_CODE_04").set(Privilege::setName, "PRIVILEGE NAME 04").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 5L).set(Privilege::setCode, "PRIVILEGE_CODE_05").set(Privilege::setName, "PRIVILEGE NAME 05").set(Privilege::setParentId, 1L).build()
                );
    }

    @Test
    void selectAllByIds_shouldWorkAsExpected() {
        assertThat(dao.selectAllByIds(Arrays.asList(2L, 3L, 4L)))
                .hasSize(3)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 2L).set(Privilege::setCode, "PRIVILEGE_CODE_02").set(Privilege::setName, "PRIVILEGE NAME 02").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 3L).set(Privilege::setCode, "PRIVILEGE_CODE_03").set(Privilege::setName, "PRIVILEGE NAME 03").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 4L).set(Privilege::setCode, "PRIVILEGE_CODE_04").set(Privilege::setName, "PRIVILEGE NAME 04").set(Privilege::setParentId, 1L).build()
                );
    }

    @Test
    void existsByQuery_shouldWorkAsExpected() {
        assertThat(dao.existsByQuery(PrivilegeQuery.builder().id(1L).build())).isTrue();
        assertThat(dao.existsByQuery(PrivilegeQuery.builder().code("PRIVILEGE_CODE_01").build())).isTrue();
        assertThat(dao.existsByQuery(PrivilegeQuery.builder().name("PRIVILEGE NAME 01").build())).isTrue();
        assertThat(dao.existsByQuery(PrivilegeQuery.builder().parentId(1L).build())).isTrue();

        assertThat(dao.existsByQuery(PrivilegeQuery.builder().id(100L).build())).isFalse();
        assertThat(dao.existsByQuery(PrivilegeQuery.builder().code("PRIVILEGE_CODE_100").build())).isFalse();
        assertThat(dao.existsByQuery(PrivilegeQuery.builder().name("PRIVILEGE NAME 100").build())).isFalse();
        assertThat(dao.existsByQuery(PrivilegeQuery.builder().parentId(100L).build())).isFalse();
    }

    @Test
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(PrivilegeQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(PrivilegeQuery.builder().code("PRIVILEGE_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(PrivilegeQuery.builder().name("PRIVILEGE NAME 01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(PrivilegeQuery.builder().parentId(1L).build())).isEqualTo(4L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        PrivilegeQuery query = PrivilegeQuery.builder()
                .parentId(1L)
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 2L).set(Privilege::setCode, "PRIVILEGE_CODE_02").set(Privilege::setName, "PRIVILEGE NAME 02").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 3L).set(Privilege::setCode, "PRIVILEGE_CODE_03").set(Privilege::setName, "PRIVILEGE NAME 03").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 4L).set(Privilege::setCode, "PRIVILEGE_CODE_04").set(Privilege::setName, "PRIVILEGE NAME 04").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 5L).set(Privilege::setCode, "PRIVILEGE_CODE_05").set(Privilege::setName, "PRIVILEGE NAME 05").set(Privilege::setParentId, 1L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(Privilege::getId).reversed());
    }

    @Test
    void selectByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectByQuery(PrivilegeQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.selectByQuery(PrivilegeQuery.builder().code("PRIVILEGE_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.selectByQuery(PrivilegeQuery.builder().name("PRIVILEGE NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
    }

    @Test
    void selectIdByCode_shouldWorkAsExpected() {
        assertThat(dao.selectIdByCode("PRIVILEGE_CODE_01")).isEqualTo(1L);
        assertThat(dao.selectIdByCode("PRIVILEGE_CODE_100")).isNull();
    }

    @Test
    void selectAllIdsByIds_shouldWorkAsExpected() {
        assertThat(dao.selectAllIdsByIds(Arrays.asList(9L, 10L, 11L))).containsExactly(9L, 10L);
    }

    @Test
    void existsById_shouldWorkAsExpected() {
        assertThat(dao.existsById(1L)).isTrue();
        assertThat(dao.existsById(100L)).isFalse();
    }

    @Test
    void existsByParentId_shouldWorkAsExpected() {
        assertThat(dao.existsByParentId(1L)).isTrue();
        assertThat(dao.existsByParentId(100L)).isFalse();
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        Privilege entity = GenericBuilder.of(Privilege::new)
                .set(Privilege::setId, 11L)
                .set(Privilege::setCode, "PRIVILEGE_CODE_11")
                .set(Privilege::setName, "PRIVILEGE NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        Privilege entity = GenericBuilder.of(Privilege::new)
                .set(Privilege::setId, 1L)
                .set(Privilege::setCode, "PRIVILEGE_CODE_12")
                .set(Privilege::setName, "PRIVILEGE NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void deleteById_shouldWorkAsExpected() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectById(2L))
                .isNull();
    }
}
