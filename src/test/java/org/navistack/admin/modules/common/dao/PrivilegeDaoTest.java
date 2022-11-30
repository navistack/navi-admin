package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.common.query.PrivilegeQuery;
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
@Sql(scripts = "classpath:data/privilege.sql")
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
    void testSelect() {
        assertThat(dao.select(PrivilegeQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.select(PrivilegeQuery.builder().code("PRIVILEGE_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.select(PrivilegeQuery.builder().name("PRIVILEGE NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.select(PrivilegeQuery.builder().parentId(1L).build()))
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
    void testSelectByIds() {
        assertThat(dao.selectByIds(Arrays.asList(2L, 3L, 4L)))
                .hasSize(3)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentId")
                .containsExactly(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 2L).set(Privilege::setCode, "PRIVILEGE_CODE_02").set(Privilege::setName, "PRIVILEGE NAME 02").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 3L).set(Privilege::setCode, "PRIVILEGE_CODE_03").set(Privilege::setName, "PRIVILEGE NAME 03").set(Privilege::setParentId, 1L).build(),
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 4L).set(Privilege::setCode, "PRIVILEGE_CODE_04").set(Privilege::setName, "PRIVILEGE NAME 04").set(Privilege::setParentId, 1L).build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(PrivilegeQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(PrivilegeQuery.builder().code("PRIVILEGE_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.count(PrivilegeQuery.builder().name("PRIVILEGE NAME 01").build())).isEqualTo(1L);
        assertThat(dao.count(PrivilegeQuery.builder().parentId(1L).build())).isEqualTo(4L);
    }

    @Test
    void testSelectWithPageable() {
        PrivilegeQuery query = PrivilegeQuery.builder()
                .parentId(1L)
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
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
    void testSelectOne() {
        assertThat(dao.selectOne(PrivilegeQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.selectOne(PrivilegeQuery.builder().code("PRIVILEGE_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
        assertThat(dao.selectOne(PrivilegeQuery.builder().name("PRIVILEGE NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(
                        GenericBuilder.of(Privilege::new).set(Privilege::setId, 1L).set(Privilege::setCode, "PRIVILEGE_CODE_01").set(Privilege::setName, "PRIVILEGE NAME 01").build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        Privilege entity = GenericBuilder.of(Privilege::new)
                .set(Privilege::setId, 11L)
                .set(Privilege::setCode, "PRIVILEGE_CODE_11")
                .set(Privilege::setName, "PRIVILEGE NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        Privilege entity = GenericBuilder.of(Privilege::new)
                .set(Privilege::setId, 1L)
                .set(Privilege::setCode, "PRIVILEGE_CODE_12")
                .set(Privilege::setName, "PRIVILEGE NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentId")
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
