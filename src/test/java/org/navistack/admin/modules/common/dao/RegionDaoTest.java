package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.dtobj.RegionDo;
import org.navistack.admin.modules.common.query.RegionQuery;
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
@Sql(scripts = "classpath:data/common/region.sql")
class RegionDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private RegionDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void selectAll_shouldWorkAsExpected() {
        assertThat(dao.selectAll())
                .hasSize(10)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 1L).set(RegionDo::setCode, "REGION_CODE_01").set(RegionDo::setName, "REGION NAME 01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 2L).set(RegionDo::setCode, "REGION_CODE_02").set(RegionDo::setName, "REGION NAME 02").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 3L).set(RegionDo::setCode, "REGION_CODE_03").set(RegionDo::setName, "REGION NAME 03").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 4L).set(RegionDo::setCode, "REGION_CODE_04").set(RegionDo::setName, "REGION NAME 04").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 5L).set(RegionDo::setCode, "REGION_CODE_05").set(RegionDo::setName, "REGION NAME 05").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 6L).set(RegionDo::setCode, "REGION_CODE_06").set(RegionDo::setName, "REGION NAME 06").set(RegionDo::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 7L).set(RegionDo::setCode, "REGION_CODE_07").set(RegionDo::setName, "REGION NAME 07").set(RegionDo::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 8L).set(RegionDo::setCode, "REGION_CODE_08").set(RegionDo::setName, "REGION NAME 08").set(RegionDo::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 9L).set(RegionDo::setCode, "REGION_CODE_09").set(RegionDo::setName, "REGION NAME 09").set(RegionDo::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 10L).set(RegionDo::setCode, "REGION_CODE_10").set(RegionDo::setName, "REGION NAME 10").set(RegionDo::setParentCode, "REGION_CODE_08").build()
                );
    }

    @Test
    void selectAllHierarchicalByCode_shouldWorkAsExpected() {
        assertThat(dao.selectAllHierarchicalByCode("REGION_CODE_05"))
                .hasSize(6)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 5L).set(RegionDo::setCode, "REGION_CODE_05").set(RegionDo::setName, "REGION NAME 05").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 6L).set(RegionDo::setCode, "REGION_CODE_06").set(RegionDo::setName, "REGION NAME 06").set(RegionDo::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 7L).set(RegionDo::setCode, "REGION_CODE_07").set(RegionDo::setName, "REGION NAME 07").set(RegionDo::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 8L).set(RegionDo::setCode, "REGION_CODE_08").set(RegionDo::setName, "REGION NAME 08").set(RegionDo::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 9L).set(RegionDo::setCode, "REGION_CODE_09").set(RegionDo::setName, "REGION NAME 09").set(RegionDo::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 10L).set(RegionDo::setCode, "REGION_CODE_10").set(RegionDo::setName, "REGION NAME 10").set(RegionDo::setParentCode, "REGION_CODE_08").build()
                );
    }

    @Test
    void selectAllByParentCode_shouldWorkAsExpectedly() {
        assertThat(dao.selectAllByParentCode("REGION_CODE_01"))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 2L).set(RegionDo::setCode, "REGION_CODE_02").set(RegionDo::setName, "REGION NAME 02").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 3L).set(RegionDo::setCode, "REGION_CODE_03").set(RegionDo::setName, "REGION NAME 03").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 4L).set(RegionDo::setCode, "REGION_CODE_04").set(RegionDo::setName, "REGION NAME 04").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 5L).set(RegionDo::setCode, "REGION_CODE_05").set(RegionDo::setName, "REGION NAME 05").set(RegionDo::setParentCode, "REGION_CODE_01").build()
                );
    }

    @Test
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(RegionQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(RegionQuery.builder().code("REGION_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(RegionQuery.builder().name("REGION NAME 01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(RegionQuery.builder().parentCode("REGION_CODE_01").build())).isEqualTo(4L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        RegionQuery query = RegionQuery.builder()
                .parentCode("REGION_CODE_01")
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 2L).set(RegionDo::setCode, "REGION_CODE_02").set(RegionDo::setName, "REGION NAME 02").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 3L).set(RegionDo::setCode, "REGION_CODE_03").set(RegionDo::setName, "REGION NAME 03").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 4L).set(RegionDo::setCode, "REGION_CODE_04").set(RegionDo::setName, "REGION NAME 04").set(RegionDo::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 5L).set(RegionDo::setCode, "REGION_CODE_05").set(RegionDo::setName, "REGION NAME 05").set(RegionDo::setParentCode, "REGION_CODE_01").build()
                ).isSortedAccordingTo(Comparator.comparingLong(RegionDo::getId).reversed());
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(RegionDo::new).set(RegionDo::setId, 1L).set(RegionDo::setCode, "REGION_CODE_01").set(RegionDo::setName, "REGION NAME 01").build()
                );
    }

    @Test
    void existsById_shouldWorkAsExpected() {
        assertThat(dao.existsById(1L)).isTrue();
        assertThat(dao.existsById(100L)).isFalse();
    }

    @Test
    void existsByCode_shouldWorkAsExpected() {
        assertThat(dao.existsByCode("REGION_CODE_01")).isTrue();
        assertThat(dao.existsByCode("REGION_CODE_100")).isFalse();
    }

    @Test
    void existsByParentCode_shouldWorkAsExpected() {
        assertThat(dao.existsByParentCode("REGION_CODE_01")).isTrue();
        assertThat(dao.existsByParentCode("REGION_CODE_100")).isFalse();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        RegionDo dtObj = GenericBuilder.of(RegionDo::new)
                .set(RegionDo::setCode, "REGION_CODE_11")
                .set(RegionDo::setName, "REGION NAME 11")
                .build();
        assertThat(dao.insert(dtObj)).isEqualTo(1);
        assertThat(dtObj.getId()).isNotNull();
        assertThat(dao.selectById(dtObj.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        RegionDo dtObj = GenericBuilder.of(RegionDo::new)
                .set(RegionDo::setId, 1L)
                .set(RegionDo::setCode, "REGION_CODE_12")
                .set(RegionDo::setName, "REGION NAME 12")
                .build();
        assertThat(dao.updateById(dtObj)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
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
