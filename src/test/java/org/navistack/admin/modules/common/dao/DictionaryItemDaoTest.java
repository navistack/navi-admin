package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
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
@Sql(scripts = "classpath:data/common/dictionary_item.sql")
class DictionaryItemDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private DictionaryItemDao dao;

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
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "code", "dictionary_id")
                .containsExactly(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 2L).set(DictionaryItem::setName, "DICT ITEM NAME 02").set(DictionaryItem::setCode, "DICT_ITEM_CODE_02").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 3L).set(DictionaryItem::setName, "DICT ITEM NAME 03").set(DictionaryItem::setCode, "DICT_ITEM_CODE_03").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 4L).set(DictionaryItem::setName, "DICT ITEM NAME 04").set(DictionaryItem::setCode, "DICT_ITEM_CODE_04").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 5L).set(DictionaryItem::setName, "DICT ITEM NAME 05").set(DictionaryItem::setCode, "DICT_ITEM_CODE_05").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 6L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 7L).set(DictionaryItem::setName, "DICT ITEM NAME 02").set(DictionaryItem::setCode, "DICT_ITEM_CODE_02").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 8L).set(DictionaryItem::setName, "DICT ITEM NAME 03").set(DictionaryItem::setCode, "DICT_ITEM_CODE_03").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 9L).set(DictionaryItem::setName, "DICT ITEM NAME 04").set(DictionaryItem::setCode, "DICT_ITEM_CODE_04").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 10L).set(DictionaryItem::setName, "DICT ITEM NAME 05").set(DictionaryItem::setCode, "DICT_ITEM_CODE_05").set(DictionaryItem::setDictionaryId, 2L).build()
                );
    }

    @Test
    void testSelect() {
        assertThat(dao.select(DictionaryItemQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "code", "dictionary_id")
                .containsExactly(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build()
                );
        assertThat(dao.select(DictionaryItemQuery.builder().name("DICT ITEM NAME 01").build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "code", "dictionary_id")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 6L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 2L).build()
                );
        assertThat(dao.select(DictionaryItemQuery.builder().code("DICT_ITEM_CODE_01").build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "code", "dictionary_id")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 6L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 2L).build()
                );
        assertThat(dao.select(DictionaryItemQuery.builder().dictionaryId(1L).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "code", "dictionary_id")
                .containsExactly(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 2L).set(DictionaryItem::setName, "DICT ITEM NAME 02").set(DictionaryItem::setCode, "DICT_ITEM_CODE_02").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 3L).set(DictionaryItem::setName, "DICT ITEM NAME 03").set(DictionaryItem::setCode, "DICT_ITEM_CODE_03").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 4L).set(DictionaryItem::setName, "DICT ITEM NAME 04").set(DictionaryItem::setCode, "DICT_ITEM_CODE_04").set(DictionaryItem::setDictionaryId, 1L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 5L).set(DictionaryItem::setName, "DICT ITEM NAME 05").set(DictionaryItem::setCode, "DICT_ITEM_CODE_05").set(DictionaryItem::setDictionaryId, 1L).build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(DictionaryItemQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(DictionaryItemQuery.builder().name("DICT ITEM NAME 01").build())).isEqualTo(2L);
        assertThat(dao.count(DictionaryItemQuery.builder().code("DICT_ITEM_CODE_01").build())).isEqualTo(2L);
        assertThat(dao.count(DictionaryItemQuery.builder().dictionaryId(1L).build())).isEqualTo(5L);
    }

    @Test
    void testSelectWithPageable() {
        DictionaryItemQuery query = DictionaryItemQuery.builder()
                .dictionaryId(2L)
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "code", "dictionary_id")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 6L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 7L).set(DictionaryItem::setName, "DICT ITEM NAME 02").set(DictionaryItem::setCode, "DICT_ITEM_CODE_02").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 8L).set(DictionaryItem::setName, "DICT ITEM NAME 03").set(DictionaryItem::setCode, "DICT_ITEM_CODE_03").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 9L).set(DictionaryItem::setName, "DICT ITEM NAME 04").set(DictionaryItem::setCode, "DICT_ITEM_CODE_04").set(DictionaryItem::setDictionaryId, 2L).build(),
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 10L).set(DictionaryItem::setName, "DICT ITEM NAME 05").set(DictionaryItem::setCode, "DICT_ITEM_CODE_05").set(DictionaryItem::setDictionaryId, 2L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(DictionaryItem::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(DictionaryItemQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "code", "dictionary_id")
                .isEqualTo(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build()
                );
        assertThat(dao.selectOne(DictionaryItemQuery.builder().dictionaryId(1L).name("DICT ITEM NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "code", "dictionary_id")
                .isEqualTo(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build()
                );
        assertThat(dao.selectOne(DictionaryItemQuery.builder().dictionaryId(1L).code("DICT_ITEM_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "code", "dictionary_id")
                .isEqualTo(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "code", "dictionary_id")
                .isEqualTo(
                        GenericBuilder.of(DictionaryItem::new).set(DictionaryItem::setId, 1L).set(DictionaryItem::setName, "DICT ITEM NAME 01").set(DictionaryItem::setCode, "DICT_ITEM_CODE_01").set(DictionaryItem::setDictionaryId, 1L).build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        DictionaryItem entity = GenericBuilder.of(DictionaryItem::new)
                .set(DictionaryItem::setId, 11L)
                .set(DictionaryItem::setName, "DICT ITEM NAME 11")
                .set(DictionaryItem::setCode, "DICT_ITEM_CODE_11")
                .set(DictionaryItem::setDictionaryId, 3L)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "code", "dictionary_id")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        DictionaryItem entity = GenericBuilder.of(DictionaryItem::new)
                .set(DictionaryItem::setId, 1L)
                .set(DictionaryItem::setName, "DICT ITEM NAME 12")
                .set(DictionaryItem::setCode, "DICT_ITEM_CODE_12")
                .set(DictionaryItem::setDictionaryId, 3L)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "code", "dictionary_id")
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
