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
    void selectAll_shouldWorkAsExpected() {
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
    void selectAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQuery(DictionaryQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(DictionaryQuery.builder().code("DICT_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectAllByQuery(DictionaryQuery.builder().name("DICT NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name")
                .containsExactly(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void existsByQuery_shouldWorkAsExpected() {
        assertThat(dao.existsByQuery(DictionaryQuery.builder().id(1L).build())).isTrue();
        assertThat(dao.existsByQuery(DictionaryQuery.builder().code("DICT_CODE_01").build())).isTrue();
        assertThat(dao.existsByQuery(DictionaryQuery.builder().name("DICT NAME 01").build())).isTrue();

        assertThat(dao.existsByQuery(DictionaryQuery.builder().id(100L).build())).isFalse();
        assertThat(dao.existsByQuery(DictionaryQuery.builder().code("DICT_CODE_100").build())).isFalse();
        assertThat(dao.existsByQuery(DictionaryQuery.builder().name("DICT NAME 100").build())).isFalse();
    }

    @Test
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(DictionaryQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(DictionaryQuery.builder().code("DICT_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.countByQuery(DictionaryQuery.builder().name("DICT NAME 01").build())).isEqualTo(1L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        DictionaryQuery query = DictionaryQuery.builder()
                .name("DICT NAME")
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
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 6L).set(Dictionary::setCode, "DICT_CODE_06").set(Dictionary::setName, "DICT NAME 06").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 7L).set(Dictionary::setCode, "DICT_CODE_07").set(Dictionary::setName, "DICT NAME 07").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 8L).set(Dictionary::setCode, "DICT_CODE_08").set(Dictionary::setName, "DICT NAME 08").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 9L).set(Dictionary::setCode, "DICT_CODE_09").set(Dictionary::setName, "DICT NAME 09").build(),
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 10L).set(Dictionary::setCode, "DICT_CODE_10").set(Dictionary::setName, "DICT NAME 10").build()
                ).isSortedAccordingTo(Comparator.comparingLong(Dictionary::getId).reversed());
    }

    @Test
    void selectByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectByQuery(DictionaryQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectByQuery(DictionaryQuery.builder().code("DICT_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
        assertThat(dao.selectByQuery(DictionaryQuery.builder().name("DICT NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void selectIdByCode_shouldWorkAsExpected() {
        assertThat(dao.selectIdByCode("DICT_CODE_01")).isEqualTo(1L);
        assertThat(dao.selectIdByCode("DICT_CODE_0100")).isNull();
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(Dictionary::new).set(Dictionary::setId, 1L).set(Dictionary::setCode, "DICT_CODE_01").set(Dictionary::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void existsById_shouldWorkAsExpected() {
        assertThat(dao.existsById(1L)).isTrue();
        assertThat(dao.existsById(100L)).isFalse();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        Dictionary entity = GenericBuilder.of(Dictionary::new)
                .set(Dictionary::setId, 11L)
                .set(Dictionary::setCode, "DICT_CODE_11")
                .set(Dictionary::setName, "DICT NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        Dictionary entity = GenericBuilder.of(Dictionary::new)
                .set(Dictionary::setId, 1L)
                .set(Dictionary::setCode, "DICT_CODE_12")
                .set(Dictionary::setName, "DICT NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
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
