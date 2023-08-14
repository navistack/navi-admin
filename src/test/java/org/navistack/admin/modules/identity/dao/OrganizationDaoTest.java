package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.dtobj.OrganizationDo;
import org.navistack.admin.modules.identity.query.OrganizationQuery;
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
@Sql(scripts = "classpath:data/identity/organization.sql")
class OrganizationDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private OrganizationDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(OrganizationQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(OrganizationQuery.builder().code("ORG_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(OrganizationQuery.builder().name("ORG NAME 01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(OrganizationQuery.builder().superId(1L).build())).isEqualTo(4L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        OrganizationQuery query = OrganizationQuery.builder()
                .superId(1L)
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(OrganizationDo::new).set(OrganizationDo::setId, 2L).set(OrganizationDo::setCode, "ORG_CODE_02").set(OrganizationDo::setName, "ORG NAME 02").set(OrganizationDo::setSuperId, 1L).build(),
                        GenericBuilder.of(OrganizationDo::new).set(OrganizationDo::setId, 3L).set(OrganizationDo::setCode, "ORG_CODE_03").set(OrganizationDo::setName, "ORG NAME 03").set(OrganizationDo::setSuperId, 1L).build(),
                        GenericBuilder.of(OrganizationDo::new).set(OrganizationDo::setId, 4L).set(OrganizationDo::setCode, "ORG_CODE_04").set(OrganizationDo::setName, "ORG NAME 04").set(OrganizationDo::setSuperId, 1L).build(),
                        GenericBuilder.of(OrganizationDo::new).set(OrganizationDo::setId, 5L).set(OrganizationDo::setCode, "ORG_CODE_05").set(OrganizationDo::setName, "ORG NAME 05").set(OrganizationDo::setSuperId, 1L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(OrganizationDo::getId).reversed());
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(OrganizationDo::new).set(OrganizationDo::setId, 1L).set(OrganizationDo::setCode, "ORG_CODE_01").set(OrganizationDo::setName, "ORG NAME 01").build()
                );
    }

    @Test
    void selectIdByCode_shouldWorkAsExpected() {
        assertThat(dao.selectIdByCode("ORG_CODE_01")).isEqualTo(1L);
        assertThat(dao.selectIdByCode("ORG_CODE_100")).isNull();
    }

    @Test
    void existsById_shouldWorkAsExpected() {
        assertThat(dao.existsById(1L)).isTrue();
        assertThat(dao.existsById(100L)).isFalse();
    }

    @Test
    void existsBySuperId_shouldWorkAsExpected() {
        assertThat(dao.existsBySuperId(1L)).isTrue();
        assertThat(dao.existsBySuperId(100L)).isFalse();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        OrganizationDo dtObj = GenericBuilder.of(OrganizationDo::new)
                .set(OrganizationDo::setCode, "ORG_CODE_11")
                .set(OrganizationDo::setName, "ORG NAME 11")
                .build();
        assertThat(dao.insert(dtObj)).isEqualTo(1);
        assertThat(dtObj.getId()).isNotNull();
        assertThat(dao.selectById(dtObj.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        OrganizationDo dtObj = GenericBuilder.of(OrganizationDo::new)
                .set(OrganizationDo::setId, 1L)
                .set(OrganizationDo::setCode, "ORG_CODE_12")
                .set(OrganizationDo::setName, "ORG NAME 12")
                .build();
        assertThat(dao.updateById(dtObj)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
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
