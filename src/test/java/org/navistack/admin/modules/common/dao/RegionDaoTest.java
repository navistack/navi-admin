package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.Region;
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
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 2L).set(Region::setCode, "REGION_CODE_02").set(Region::setName, "REGION NAME 02").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 3L).set(Region::setCode, "REGION_CODE_03").set(Region::setName, "REGION NAME 03").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 4L).set(Region::setCode, "REGION_CODE_04").set(Region::setName, "REGION NAME 04").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 5L).set(Region::setCode, "REGION_CODE_05").set(Region::setName, "REGION NAME 05").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 6L).set(Region::setCode, "REGION_CODE_06").set(Region::setName, "REGION NAME 06").set(Region::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 7L).set(Region::setCode, "REGION_CODE_07").set(Region::setName, "REGION NAME 07").set(Region::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 8L).set(Region::setCode, "REGION_CODE_08").set(Region::setName, "REGION NAME 08").set(Region::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 9L).set(Region::setCode, "REGION_CODE_09").set(Region::setName, "REGION NAME 09").set(Region::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 10L).set(Region::setCode, "REGION_CODE_10").set(Region::setName, "REGION NAME 10").set(Region::setParentCode, "REGION_CODE_08").build()
                );
    }

    @Test
    void selectAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQuery(RegionQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(RegionQuery.builder().code("REGION_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(RegionQuery.builder().name("REGION NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(RegionQuery.builder().parentCode("REGION_CODE_01").build()))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 2L).set(Region::setCode, "REGION_CODE_02").set(Region::setName, "REGION NAME 02").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 3L).set(Region::setCode, "REGION_CODE_03").set(Region::setName, "REGION NAME 03").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 4L).set(Region::setCode, "REGION_CODE_04").set(Region::setName, "REGION NAME 04").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 5L).set(Region::setCode, "REGION_CODE_05").set(Region::setName, "REGION NAME 05").set(Region::setParentCode, "REGION_CODE_01").build()
                );
    }

    @Test
    void selectAllByQueryRecursively_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQueryRecursively(RegionQuery.builder().code("REGION_CODE_05").build()))
                .hasSize(6)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(Region::new).set(Region::setId, 5L).set(Region::setCode, "REGION_CODE_05").set(Region::setName, "REGION NAME 05").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 6L).set(Region::setCode, "REGION_CODE_06").set(Region::setName, "REGION NAME 06").set(Region::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 7L).set(Region::setCode, "REGION_CODE_07").set(Region::setName, "REGION NAME 07").set(Region::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 8L).set(Region::setCode, "REGION_CODE_08").set(Region::setName, "REGION NAME 08").set(Region::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 9L).set(Region::setCode, "REGION_CODE_09").set(Region::setName, "REGION NAME 09").set(Region::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 10L).set(Region::setCode, "REGION_CODE_10").set(Region::setName, "REGION NAME 10").set(Region::setParentCode, "REGION_CODE_08").build()
                );
    }

    @Test
    void selectAllHierarchicalByCode_shouldWorkAsExpected() {
        assertThat(dao.selectAllHierarchicalByCode("REGION_CODE_05"))
                .hasSize(6)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(Region::new).set(Region::setId, 5L).set(Region::setCode, "REGION_CODE_05").set(Region::setName, "REGION NAME 05").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 6L).set(Region::setCode, "REGION_CODE_06").set(Region::setName, "REGION NAME 06").set(Region::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 7L).set(Region::setCode, "REGION_CODE_07").set(Region::setName, "REGION NAME 07").set(Region::setParentCode, "REGION_CODE_05").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 8L).set(Region::setCode, "REGION_CODE_08").set(Region::setName, "REGION NAME 08").set(Region::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 9L).set(Region::setCode, "REGION_CODE_09").set(Region::setName, "REGION NAME 09").set(Region::setParentCode, "REGION_CODE_06").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 10L).set(Region::setCode, "REGION_CODE_10").set(Region::setName, "REGION NAME 10").set(Region::setParentCode, "REGION_CODE_08").build()
                );
    }

    @Test
    void selectAllByParentCode_shouldWorkAsExpectedly() {
        assertThat(dao.selectAllByParentCode("REGION_CODE_01"))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 2L).set(Region::setCode, "REGION_CODE_02").set(Region::setName, "REGION NAME 02").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 3L).set(Region::setCode, "REGION_CODE_03").set(Region::setName, "REGION NAME 03").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 4L).set(Region::setCode, "REGION_CODE_04").set(Region::setName, "REGION NAME 04").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 5L).set(Region::setCode, "REGION_CODE_05").set(Region::setName, "REGION NAME 05").set(Region::setParentCode, "REGION_CODE_01").build()
                );
    }

    @Test
    void existsByQuery_shouldWorkAsExpected() {
        assertThat(dao.existsByQuery(RegionQuery.builder().id(1L).build())).isTrue();
        assertThat(dao.existsByQuery(RegionQuery.builder().code("REGION_CODE_01").build())).isTrue();
        assertThat(dao.existsByQuery(RegionQuery.builder().name("REGION NAME 01").build())).isTrue();
        assertThat(dao.existsByQuery(RegionQuery.builder().parentCode("REGION_CODE_01").build())).isTrue();

        assertThat(dao.existsByQuery(RegionQuery.builder().id(100L).build())).isFalse();
        assertThat(dao.existsByQuery(RegionQuery.builder().code("REGION_CODE_100").build())).isFalse();
        assertThat(dao.existsByQuery(RegionQuery.builder().name("REGION NAME 100").build())).isFalse();
        assertThat(dao.existsByQuery(RegionQuery.builder().parentCode("REGION_CODE_100").build())).isFalse();
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
                        GenericBuilder.of(Region::new).set(Region::setId, 2L).set(Region::setCode, "REGION_CODE_02").set(Region::setName, "REGION NAME 02").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 3L).set(Region::setCode, "REGION_CODE_03").set(Region::setName, "REGION NAME 03").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 4L).set(Region::setCode, "REGION_CODE_04").set(Region::setName, "REGION NAME 04").set(Region::setParentCode, "REGION_CODE_01").build(),
                        GenericBuilder.of(Region::new).set(Region::setId, 5L).set(Region::setCode, "REGION_CODE_05").set(Region::setName, "REGION NAME 05").set(Region::setParentCode, "REGION_CODE_01").build()
                ).isSortedAccordingTo(Comparator.comparingLong(Region::getId).reversed());
    }

    @Test
    void selectByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectByQuery(RegionQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.selectByQuery(RegionQuery.builder().code("REGION_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.selectByQuery(RegionQuery.builder().name("REGION NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
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
        Region entity = GenericBuilder.of(Region::new)
                .set(Region::setCode, "REGION_CODE_11")
                .set(Region::setName, "REGION NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(entity.getId()).isNotNull();
        assertThat(dao.selectById(entity.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        Region entity = GenericBuilder.of(Region::new)
                .set(Region::setId, 1L)
                .set(Region::setCode, "REGION_CODE_12")
                .set(Region::setName, "REGION NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
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
