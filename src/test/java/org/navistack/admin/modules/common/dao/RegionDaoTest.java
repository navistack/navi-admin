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
@Sql(scripts = "classpath:data/region.sql")
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
    void testSelectAll() {
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
    void testSelect() {
        assertThat(dao.select(RegionQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.select(RegionQuery.builder().code("REGION_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.select(RegionQuery.builder().name("REGION NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "parentCode")
                .containsExactly(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.select(RegionQuery.builder().parentCode("REGION_CODE_01").build()))
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
    void testSelectRecursively() {
        assertThat(dao.selectRecursively(RegionQuery.builder().code("REGION_CODE_05").build()))
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
    void testCount() {
        assertThat(dao.count(RegionQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(RegionQuery.builder().code("REGION_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.count(RegionQuery.builder().name("REGION NAME 01").build())).isEqualTo(1L);
        assertThat(dao.count(RegionQuery.builder().parentCode("REGION_CODE_01").build())).isEqualTo(4L);
    }

    @Test
    void testSelectWithPageable() {
        RegionQuery query = RegionQuery.builder()
                .parentCode("REGION_CODE_01")
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
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
    void testSelectOne() {
        assertThat(dao.selectOne(RegionQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.selectOne(RegionQuery.builder().code("REGION_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
        assertThat(dao.selectOne(RegionQuery.builder().name("REGION NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(
                        GenericBuilder.of(Region::new).set(Region::setId, 1L).set(Region::setCode, "REGION_CODE_01").set(Region::setName, "REGION NAME 01").build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        Region entity = GenericBuilder.of(Region::new)
                .set(Region::setId, 11L)
                .set(Region::setCode, "REGION_CODE_11")
                .set(Region::setName, "REGION NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        Region entity = GenericBuilder.of(Region::new)
                .set(Region::setId, 1L)
                .set(Region::setCode, "REGION_CODE_12")
                .set(Region::setName, "REGION NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "parentCode")
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
