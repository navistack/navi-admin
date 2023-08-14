package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.dtobj.RoleDo;
import org.navistack.admin.modules.identity.query.RoleQuery;
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
@Sql(scripts = "classpath:data/identity/role.sql")
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
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(RoleQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(RoleQuery.builder().code("ROLE_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(RoleQuery.builder().name("ROLE NAME 01").build())).isEqualTo(1L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        RoleQuery query = RoleQuery.builder()
                .name("ROLE NAME")
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RoleDo::new).set(RoleDo::setId, 6L).set(RoleDo::setCode, "ROLE_CODE_06").set(RoleDo::setName, "ROLE NAME 06").build(),
                        GenericBuilder.of(RoleDo::new).set(RoleDo::setId, 7L).set(RoleDo::setCode, "ROLE_CODE_07").set(RoleDo::setName, "ROLE NAME 07").build(),
                        GenericBuilder.of(RoleDo::new).set(RoleDo::setId, 8L).set(RoleDo::setCode, "ROLE_CODE_08").set(RoleDo::setName, "ROLE NAME 08").build(),
                        GenericBuilder.of(RoleDo::new).set(RoleDo::setId, 9L).set(RoleDo::setCode, "ROLE_CODE_09").set(RoleDo::setName, "ROLE NAME 09").build(),
                        GenericBuilder.of(RoleDo::new).set(RoleDo::setId, 10L).set(RoleDo::setCode, "ROLE_CODE_10").set(RoleDo::setName, "ROLE NAME 10").build()
                ).isSortedAccordingTo(Comparator.comparingLong(RoleDo::getId).reversed());
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(RoleDo::new).set(RoleDo::setId, 1L).set(RoleDo::setCode, "ROLE_CODE_01").set(RoleDo::setName, "ROLE NAME 01").build()
                );
    }

    @Test
    void selectIdByCode_shouldWorkAsExpected() {
        assertThat(dao.selectIdByCode("ROLE_CODE_01")).isEqualTo(1L);
        assertThat(dao.selectIdByCode("ROLE_CODE_100")).isNull();
    }

    @Test
    void selectAllIdsByIds_shouldWorkAsExpected() {
        assertThat(dao.selectAllIdsByIds(Arrays.asList(9L, 10L, 11L))).containsExactly(9L, 10L);
    }

    @Test
    void existsByIdshouldWorkAsExpected() {
        assertThat(dao.existsById(1L)).isTrue();
        assertThat(dao.existsById(100L)).isFalse();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        RoleDo dtObj = GenericBuilder.of(RoleDo::new)
                .set(RoleDo::setCode, "ROLE_CODE_11")
                .set(RoleDo::setName, "ROLE NAME 11")
                .build();
        assertThat(dao.insert(dtObj)).isEqualTo(1);
        assertThat(dtObj.getId()).isNotNull();
        assertThat(dao.selectById(dtObj.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        RoleDo dtObj = GenericBuilder.of(RoleDo::new)
                .set(RoleDo::setId, 1L)
                .set(RoleDo::setCode, "ROLE_CODE_12")
                .set(RoleDo::setName, "ROLE NAME 12")
                .build();
        assertThat(dao.updateById(dtObj)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void deleteById_shouldWorkAsExpected() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectById(2L))
                .isNull();
    }
}
