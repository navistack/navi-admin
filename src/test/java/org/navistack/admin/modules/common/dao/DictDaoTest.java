package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.common.query.DictQuery;
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
@Sql(scripts = "classpath:data/dict.sql")
class DictDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private DictDao dao;

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
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 2L).set(Dict::setCode, "DICT_CODE_02").set(Dict::setName, "DICT NAME 02").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 3L).set(Dict::setCode, "DICT_CODE_03").set(Dict::setName, "DICT NAME 03").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 4L).set(Dict::setCode, "DICT_CODE_04").set(Dict::setName, "DICT NAME 04").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 5L).set(Dict::setCode, "DICT_CODE_05").set(Dict::setName, "DICT NAME 05").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 6L).set(Dict::setCode, "DICT_CODE_06").set(Dict::setName, "DICT NAME 06").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 7L).set(Dict::setCode, "DICT_CODE_07").set(Dict::setName, "DICT NAME 07").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 8L).set(Dict::setCode, "DICT_CODE_08").set(Dict::setName, "DICT NAME 08").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 9L).set(Dict::setCode, "DICT_CODE_09").set(Dict::setName, "DICT NAME 09").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 10L).set(Dict::setCode, "DICT_CODE_10").set(Dict::setName, "DICT NAME 10").build()
                );
    }

    @Test
    void testSelect() {
        assertThat(dao.select(DictQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build()
                );
        assertThat(dao.select(DictQuery.builder().code("DICT_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build()
                );
        assertThat(dao.select(DictQuery.builder().name("DICT NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(DictQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(DictQuery.builder().code("DICT_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.count(DictQuery.builder().name("DICT NAME 01").build())).isEqualTo(1L);
    }

    @Test
    void testSelectWithPageable() {
        DictQuery query = DictQuery.builder()
                .name("DICT NAME")
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
                        GenericBuilder.of(Dict::new).set(Dict::setId, 6L).set(Dict::setCode, "DICT_CODE_06").set(Dict::setName, "DICT NAME 06").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 7L).set(Dict::setCode, "DICT_CODE_07").set(Dict::setName, "DICT NAME 07").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 8L).set(Dict::setCode, "DICT_CODE_08").set(Dict::setName, "DICT NAME 08").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 9L).set(Dict::setCode, "DICT_CODE_09").set(Dict::setName, "DICT NAME 09").build(),
                        GenericBuilder.of(Dict::new).set(Dict::setId, 10L).set(Dict::setCode, "DICT_CODE_10").set(Dict::setName, "DICT NAME 10").build()
                ).isSortedAccordingTo(Comparator.comparingLong(Dict::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(DictQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectOne(DictQuery.builder().code("DICT_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectOne(DictQuery.builder().name("DICT NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dict::new).set(Dict::setId, 1L).set(Dict::setCode, "DICT_CODE_01").set(Dict::setName, "DICT NAME 01").build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        Dict entity = GenericBuilder.of(Dict::new)
                .set(Dict::setId, 11L)
                .set(Dict::setCode, "DICT_CODE_11")
                .set(Dict::setName, "DICT NAME 11")
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
        Dict entity = GenericBuilder.of(Dict::new)
                .set(Dict::setId, 1L)
                .set(Dict::setCode, "DICT_CODE_12")
                .set(Dict::setName, "DICT NAME 12")
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
