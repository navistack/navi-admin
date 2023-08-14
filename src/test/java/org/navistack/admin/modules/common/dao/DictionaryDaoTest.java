package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.dtobj.DictionaryDo;
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
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 1L).set(DictionaryDo::setCode, "DICT_CODE_01").set(DictionaryDo::setName, "DICT NAME 01").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 2L).set(DictionaryDo::setCode, "DICT_CODE_02").set(DictionaryDo::setName, "DICT NAME 02").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 3L).set(DictionaryDo::setCode, "DICT_CODE_03").set(DictionaryDo::setName, "DICT NAME 03").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 4L).set(DictionaryDo::setCode, "DICT_CODE_04").set(DictionaryDo::setName, "DICT NAME 04").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 5L).set(DictionaryDo::setCode, "DICT_CODE_05").set(DictionaryDo::setName, "DICT NAME 05").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 6L).set(DictionaryDo::setCode, "DICT_CODE_06").set(DictionaryDo::setName, "DICT NAME 06").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 7L).set(DictionaryDo::setCode, "DICT_CODE_07").set(DictionaryDo::setName, "DICT NAME 07").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 8L).set(DictionaryDo::setCode, "DICT_CODE_08").set(DictionaryDo::setName, "DICT NAME 08").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 9L).set(DictionaryDo::setCode, "DICT_CODE_09").set(DictionaryDo::setName, "DICT NAME 09").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 10L).set(DictionaryDo::setCode, "DICT_CODE_10").set(DictionaryDo::setName, "DICT NAME 10").build()
                );
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
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 6L).set(DictionaryDo::setCode, "DICT_CODE_06").set(DictionaryDo::setName, "DICT NAME 06").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 7L).set(DictionaryDo::setCode, "DICT_CODE_07").set(DictionaryDo::setName, "DICT NAME 07").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 8L).set(DictionaryDo::setCode, "DICT_CODE_08").set(DictionaryDo::setName, "DICT NAME 08").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 9L).set(DictionaryDo::setCode, "DICT_CODE_09").set(DictionaryDo::setName, "DICT NAME 09").build(),
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 10L).set(DictionaryDo::setCode, "DICT_CODE_10").set(DictionaryDo::setName, "DICT NAME 10").build()
                ).isSortedAccordingTo(Comparator.comparingLong(DictionaryDo::getId).reversed());
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
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 1L).set(DictionaryDo::setCode, "DICT_CODE_01").set(DictionaryDo::setName, "DICT NAME 01").build()
                );
    }

    @Test
    void selectByCode_shouldWorkAsExpected() {
        assertThat(dao.selectByCode("DICT_CODE_01"))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name")
                .isEqualTo(
                        GenericBuilder.of(DictionaryDo::new).set(DictionaryDo::setId, 1L).set(DictionaryDo::setCode, "DICT_CODE_01").set(DictionaryDo::setName, "DICT NAME 01").build()
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
        DictionaryDo dtObj = GenericBuilder.of(DictionaryDo::new)
                .set(DictionaryDo::setCode, "DICT_CODE_11")
                .set(DictionaryDo::setName, "DICT NAME 11")
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
        DictionaryDo dtObj = GenericBuilder.of(DictionaryDo::new)
                .set(DictionaryDo::setId, 1L)
                .set(DictionaryDo::setCode, "DICT_CODE_12")
                .set(DictionaryDo::setName, "DICT NAME 12")
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
