package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.entity.Organization;
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
    void selectAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQuery(OrganizationQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 1L).set(Organization::setCode, "ORG_CODE_01").set(Organization::setName, "ORG NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(OrganizationQuery.builder().code("ORG_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 1L).set(Organization::setCode, "ORG_CODE_01").set(Organization::setName, "ORG NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(OrganizationQuery.builder().name("ORG NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 1L).set(Organization::setCode, "ORG_CODE_01").set(Organization::setName, "ORG NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(OrganizationQuery.builder().superId(1L).build()))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 2L).set(Organization::setCode, "ORG_CODE_02").set(Organization::setName, "ORG NAME 02").set(Organization::setSuperId, 1L).build(),
                        GenericBuilder.of(Organization::new).set(Organization::setId, 3L).set(Organization::setCode, "ORG_CODE_03").set(Organization::setName, "ORG NAME 03").set(Organization::setSuperId, 1L).build(),
                        GenericBuilder.of(Organization::new).set(Organization::setId, 4L).set(Organization::setCode, "ORG_CODE_04").set(Organization::setName, "ORG NAME 04").set(Organization::setSuperId, 1L).build(),
                        GenericBuilder.of(Organization::new).set(Organization::setId, 5L).set(Organization::setCode, "ORG_CODE_05").set(Organization::setName, "ORG NAME 05").set(Organization::setSuperId, 1L).build()
                );
    }

    @Test
    void existsByQuery_shouldWorkAsExpected() {
        assertThat(dao.existsByQuery(OrganizationQuery.builder().id(1L).build())).isTrue();
        assertThat(dao.existsByQuery(OrganizationQuery.builder().code("ORG_CODE_01").build())).isTrue();
        assertThat(dao.existsByQuery(OrganizationQuery.builder().name("ORG NAME 01").build())).isTrue();
        assertThat(dao.existsByQuery(OrganizationQuery.builder().superId(1L).build())).isTrue();

        assertThat(dao.existsByQuery(OrganizationQuery.builder().id(100L).build())).isFalse();
        assertThat(dao.existsByQuery(OrganizationQuery.builder().code("ORG_CODE_100").build())).isFalse();
        assertThat(dao.existsByQuery(OrganizationQuery.builder().name("ORG NAME 100").build())).isFalse();
        assertThat(dao.existsByQuery(OrganizationQuery.builder().superId(100L).build())).isFalse();
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
                        GenericBuilder.of(Organization::new).set(Organization::setId, 2L).set(Organization::setCode, "ORG_CODE_02").set(Organization::setName, "ORG NAME 02").set(Organization::setSuperId, 1L).build(),
                        GenericBuilder.of(Organization::new).set(Organization::setId, 3L).set(Organization::setCode, "ORG_CODE_03").set(Organization::setName, "ORG NAME 03").set(Organization::setSuperId, 1L).build(),
                        GenericBuilder.of(Organization::new).set(Organization::setId, 4L).set(Organization::setCode, "ORG_CODE_04").set(Organization::setName, "ORG NAME 04").set(Organization::setSuperId, 1L).build(),
                        GenericBuilder.of(Organization::new).set(Organization::setId, 5L).set(Organization::setCode, "ORG_CODE_05").set(Organization::setName, "ORG NAME 05").set(Organization::setSuperId, 1L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(Organization::getId).reversed());
    }

    @Test
    void selectByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectByQuery(OrganizationQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 1L).set(Organization::setCode, "ORG_CODE_01").set(Organization::setName, "ORG NAME 01").build()
                );
        assertThat(dao.selectByQuery(OrganizationQuery.builder().code("ORG_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 1L).set(Organization::setCode, "ORG_CODE_01").set(Organization::setName, "ORG NAME 01").build()
                );
        assertThat(dao.selectByQuery(OrganizationQuery.builder().name("ORG NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 1L).set(Organization::setCode, "ORG_CODE_01").set(Organization::setName, "ORG NAME 01").build()
                );
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Organization::new).set(Organization::setId, 1L).set(Organization::setCode, "ORG_CODE_01").set(Organization::setName, "ORG NAME 01").build()
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
        Organization entity = GenericBuilder.of(Organization::new)
                .set(Organization::setCode, "ORG_CODE_11")
                .set(Organization::setName, "ORG NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(entity.getId()).isNotNull();
        assertThat(dao.selectById(entity.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        Organization entity = GenericBuilder.of(Organization::new)
                .set(Organization::setId, 1L)
                .set(Organization::setCode, "ORG_CODE_12")
                .set(Organization::setName, "ORG NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
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
