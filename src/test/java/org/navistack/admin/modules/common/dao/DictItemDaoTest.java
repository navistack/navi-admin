package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.common.query.DictItemQuery;
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
@Sql(scripts = "classpath:data/dict_item.sql")
class DictItemDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private DictItemDao dao;

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
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "itKey", "itValue", "dictCode")
                .containsExactly(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 2L).set(DictItem::setName, "DICT ITEM NAME 02").set(DictItem::setItKey, "DICT_ITEM_KEY_02").set(DictItem::setItValue, "DICT_ITEM_VALUE_02").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 3L).set(DictItem::setName, "DICT ITEM NAME 03").set(DictItem::setItKey, "DICT_ITEM_KEY_03").set(DictItem::setItValue, "DICT_ITEM_VALUE_03").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 4L).set(DictItem::setName, "DICT ITEM NAME 04").set(DictItem::setItKey, "DICT_ITEM_KEY_04").set(DictItem::setItValue, "DICT_ITEM_VALUE_04").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 5L).set(DictItem::setName, "DICT ITEM NAME 05").set(DictItem::setItKey, "DICT_ITEM_KEY_05").set(DictItem::setItValue, "DICT_ITEM_VALUE_05").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 6L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 7L).set(DictItem::setName, "DICT ITEM NAME 02").set(DictItem::setItKey, "DICT_ITEM_KEY_02").set(DictItem::setItValue, "DICT_ITEM_VALUE_02").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 8L).set(DictItem::setName, "DICT ITEM NAME 03").set(DictItem::setItKey, "DICT_ITEM_KEY_03").set(DictItem::setItValue, "DICT_ITEM_VALUE_03").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 9L).set(DictItem::setName, "DICT ITEM NAME 04").set(DictItem::setItKey, "DICT_ITEM_KEY_04").set(DictItem::setItValue, "DICT_ITEM_VALUE_04").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 10L).set(DictItem::setName, "DICT ITEM NAME 05").set(DictItem::setItKey, "DICT_ITEM_KEY_05").set(DictItem::setItValue, "DICT_ITEM_VALUE_05").set(DictItem::setDictCode, "DICT_CODE_02").build()
                );
    }

    @Test
    void testSelect() {
        assertThat(dao.select(DictItemQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "itKey", "itValue", "dictCode")
                .containsExactly(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build()
                );
        assertThat(dao.select(DictItemQuery.builder().name("DICT ITEM NAME 01").build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "itKey", "itValue", "dictCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 6L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_02").build()
                );
        assertThat(dao.select(DictItemQuery.builder().itKey("DICT_ITEM_KEY_01").build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "itKey", "itValue", "dictCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 6L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_02").build()
                );
        assertThat(dao.select(DictItemQuery.builder().dictCode("DICT_CODE_01").build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "itKey", "itValue", "dictCode")
                .containsExactly(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 2L).set(DictItem::setName, "DICT ITEM NAME 02").set(DictItem::setItKey, "DICT_ITEM_KEY_02").set(DictItem::setItValue, "DICT_ITEM_VALUE_02").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 3L).set(DictItem::setName, "DICT ITEM NAME 03").set(DictItem::setItKey, "DICT_ITEM_KEY_03").set(DictItem::setItValue, "DICT_ITEM_VALUE_03").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 4L).set(DictItem::setName, "DICT ITEM NAME 04").set(DictItem::setItKey, "DICT_ITEM_KEY_04").set(DictItem::setItValue, "DICT_ITEM_VALUE_04").set(DictItem::setDictCode, "DICT_CODE_01").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 5L).set(DictItem::setName, "DICT ITEM NAME 05").set(DictItem::setItKey, "DICT_ITEM_KEY_05").set(DictItem::setItValue, "DICT_ITEM_VALUE_05").set(DictItem::setDictCode, "DICT_CODE_01").build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(DictItemQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(DictItemQuery.builder().name("DICT ITEM NAME 01").build())).isEqualTo(2L);
        assertThat(dao.count(DictItemQuery.builder().itKey("DICT_ITEM_KEY_01").build())).isEqualTo(2L);
        assertThat(dao.count(DictItemQuery.builder().dictCode("DICT_CODE_01").build())).isEqualTo(5L);
    }

    @Test
    void testSelectWithPageable() {
        DictItemQuery query = DictItemQuery.builder()
                .dictCode("DICT_CODE_02")
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name", "itKey", "itValue", "dictCode")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 6L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 7L).set(DictItem::setName, "DICT ITEM NAME 02").set(DictItem::setItKey, "DICT_ITEM_KEY_02").set(DictItem::setItValue, "DICT_ITEM_VALUE_02").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 8L).set(DictItem::setName, "DICT ITEM NAME 03").set(DictItem::setItKey, "DICT_ITEM_KEY_03").set(DictItem::setItValue, "DICT_ITEM_VALUE_03").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 9L).set(DictItem::setName, "DICT ITEM NAME 04").set(DictItem::setItKey, "DICT_ITEM_KEY_04").set(DictItem::setItValue, "DICT_ITEM_VALUE_04").set(DictItem::setDictCode, "DICT_CODE_02").build(),
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 10L).set(DictItem::setName, "DICT ITEM NAME 05").set(DictItem::setItKey, "DICT_ITEM_KEY_05").set(DictItem::setItValue, "DICT_ITEM_VALUE_05").set(DictItem::setDictCode, "DICT_CODE_02").build()
                ).isSortedAccordingTo(Comparator.comparingLong(DictItem::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(DictItemQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "itKey", "itValue", "dictCode")
                .isEqualTo(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build()
                );
        assertThat(dao.selectOne(DictItemQuery.builder().dictCode("DICT_CODE_01").name("DICT ITEM NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "itKey", "itValue", "dictCode")
                .isEqualTo(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build()
                );
        assertThat(dao.selectOne(DictItemQuery.builder().dictCode("DICT_CODE_01").itKey("DICT_ITEM_KEY_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "itKey", "itValue", "dictCode")
                .isEqualTo(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "itKey", "itValue", "dictCode")
                .isEqualTo(
                        GenericBuilder.of(DictItem::new).set(DictItem::setId, 1L).set(DictItem::setName, "DICT ITEM NAME 01").set(DictItem::setItKey, "DICT_ITEM_KEY_01").set(DictItem::setItValue, "DICT_ITEM_VALUE_01").set(DictItem::setDictCode, "DICT_CODE_01").build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        DictItem entity = GenericBuilder.of(DictItem::new)
                .set(DictItem::setId, 11L)
                .set(DictItem::setName, "DICT ITEM NAME 11")
                .set(DictItem::setItKey, "DICT_ITEM_KEY_11")
                .set(DictItem::setItValue, "DICT_ITEM_VALUE_11")
                .set(DictItem::setDictCode, "DICT_CODE_03")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "itKey", "itValue", "dictCode")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        DictItem entity = GenericBuilder.of(DictItem::new)
                .set(DictItem::setId, 1L)
                .set(DictItem::setName, "DICT ITEM NAME 12")
                .set(DictItem::setItKey, "DICT_ITEM_KEY_12")
                .set(DictItem::setItValue, "DICT_ITEM_VALUE_12")
                .set(DictItem::setDictCode, "DICT_CODE_03")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name", "itKey", "itValue", "dictCode")
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
