package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.query.DictionaryQuery;
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
@Sql(scripts = "classpath:data/common/dictionary.sql")
class DictionaryDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private DictionaryDao dao;

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
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 2L).set(Dictionary::setCode, "DICT_CODE_02").set(Dictionary::setName, "DICT NAME 02").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 3L).set(Dictionary::setCode, "DICT_CODE_03").set(Dictionary::setName, "DICT NAME 03").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 4L).set(Dictionary::setCode, "DICT_CODE_04").set(Dictionary::setName, "DICT NAME 04").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 5L).set(Dictionary::setCode, "DICT_CODE_05").set(Dictionary::setName, "DICT NAME 05").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 6L).set(Dictionary::setCode, "DICT_CODE_06").set(Dictionary::setName, "DICT NAME 06").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 7L).set(Dictionary::setCode, "DICT_CODE_07").set(Dictionary::setName, "DICT NAME 07").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 8L).set(Dictionary::setCode, "DICT_CODE_08").set(Dictionary::setName, "DICT NAME 08").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 9L).set(Dictionary::setCode, "DICT_CODE_09").set(Dictionary::setName, "DICT NAME 09").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 10L).set(Dictionary::setCode, "DICT_CODE_10").set(Dictionary::setName, "DICT NAME 10").build()
                );
    }

    @Test
    void testSelect() {
        assertThat(dao.select(DictionaryQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.select(DictionaryQuery.builder().code("DICT_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.select(DictionaryQuery.builder().name("DICT NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(DictionaryQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(DictionaryQuery.builder().code("DICT_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.count(DictionaryQuery.builder().name("DICT NAME 01").build())).isEqualTo(1L);
    }

    @Test
    void testSelectWithPageable() {
        DictionaryQuery query = DictionaryQuery.builder()
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
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 6L).set(Dictionary::setCode, "DICT_CODE_06").set(Dictionary::setName, "DICT NAME 06").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 7L).set(Dictionary::setCode, "DICT_CODE_07").set(Dictionary::setName, "DICT NAME 07").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 8L).set(Dictionary::setCode, "DICT_CODE_08").set(Dictionary::setName, "DICT NAME 08").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 9L).set(Dictionary::setCode, "DICT_CODE_09").set(Dictionary::setName, "DICT NAME 09").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 10L).set(Dictionary::setCode, "DICT_CODE_10").set(Dictionary::setName, "DICT NAME 10").build()
                ).isSortedAccordingTo(Comparator.comparingLong(Dictionary::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(DictionaryQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectOne(DictionaryQuery.builder().code("DICT_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectOne(DictionaryQuery.builder().name("DICT NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        Dictionary entity = GenericBuilder.of(Dictionary::new)
                .set(Dictionary::setId, 11L)
                .set(Dictionary::setCode, "DICT_CODE_11")
                .set(Dictionary::setName, "DICT NAME 11")
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
        Dictionary entity = GenericBuilder.of(Dictionary::new)
                .set(Dictionary::setId, 1L)
                .set(Dictionary::setCode, "DICT_CODE_12")
                .set(Dictionary::setName, "DICT NAME 12")
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
