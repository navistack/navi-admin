package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.Org;
import org.navistack.admin.modules.common.query.OrgQuery;
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
@Sql(scripts = "classpath:data/org.sql")
class OrgDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private OrgDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void testSelect() {
        assertThat(dao.select(OrgQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Org::new).set(Org::setId, 1L).set(Org::setCode, "ORG_CODE_01").set(Org::setName, "ORG NAME 01").build()
                );
        assertThat(dao.select(OrgQuery.builder().code("ORG_CODE_01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Org::new).set(Org::setId, 1L).set(Org::setCode, "ORG_CODE_01").set(Org::setName, "ORG NAME 01").build()
                );
        assertThat(dao.select(OrgQuery.builder().name("ORG NAME 01").build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Org::new).set(Org::setId, 1L).set(Org::setCode, "ORG_CODE_01").set(Org::setName, "ORG NAME 01").build()
                );
        assertThat(dao.select(OrgQuery.builder().superId(1L).build()))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactly(
                        GenericBuilder.of(Org::new).set(Org::setId, 2L).set(Org::setCode, "ORG_CODE_02").set(Org::setName, "ORG NAME 02").set(Org::setSuperId, 1L).build(),
                        GenericBuilder.of(Org::new).set(Org::setId, 3L).set(Org::setCode, "ORG_CODE_03").set(Org::setName, "ORG NAME 03").set(Org::setSuperId, 1L).build(),
                        GenericBuilder.of(Org::new).set(Org::setId, 4L).set(Org::setCode, "ORG_CODE_04").set(Org::setName, "ORG NAME 04").set(Org::setSuperId, 1L).build(),
                        GenericBuilder.of(Org::new).set(Org::setId, 5L).set(Org::setCode, "ORG_CODE_05").set(Org::setName, "ORG NAME 05").set(Org::setSuperId, 1L).build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(OrgQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(OrgQuery.builder().code("ORG_CODE_01").build())).isEqualTo(1L);
        assertThat(dao.count(OrgQuery.builder().name("ORG NAME 01").build())).isEqualTo(1L);
        assertThat(dao.count(OrgQuery.builder().superId(1L).build())).isEqualTo(4L);
    }

    @Test
    void testSelectWithPageable() {
        OrgQuery query = OrgQuery.builder()
                .superId(1L)
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "code", "name", "superId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(Org::new).set(Org::setId, 2L).set(Org::setCode, "ORG_CODE_02").set(Org::setName, "ORG NAME 02").set(Org::setSuperId, 1L).build(),
                        GenericBuilder.of(Org::new).set(Org::setId, 3L).set(Org::setCode, "ORG_CODE_03").set(Org::setName, "ORG NAME 03").set(Org::setSuperId, 1L).build(),
                        GenericBuilder.of(Org::new).set(Org::setId, 4L).set(Org::setCode, "ORG_CODE_04").set(Org::setName, "ORG NAME 04").set(Org::setSuperId, 1L).build(),
                        GenericBuilder.of(Org::new).set(Org::setId, 5L).set(Org::setCode, "ORG_CODE_05").set(Org::setName, "ORG NAME 05").set(Org::setSuperId, 1L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(Org::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(OrgQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Org::new).set(Org::setId, 1L).set(Org::setCode, "ORG_CODE_01").set(Org::setName, "ORG NAME 01").build()
                );
        assertThat(dao.selectOne(OrgQuery.builder().code("ORG_CODE_01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Org::new).set(Org::setId, 1L).set(Org::setCode, "ORG_CODE_01").set(Org::setName, "ORG NAME 01").build()
                );
        assertThat(dao.selectOne(OrgQuery.builder().name("ORG NAME 01").build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Org::new).set(Org::setId, 1L).set(Org::setCode, "ORG_CODE_01").set(Org::setName, "ORG NAME 01").build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(
                        GenericBuilder.of(Org::new).set(Org::setId, 1L).set(Org::setCode, "ORG_CODE_01").set(Org::setName, "ORG NAME 01").build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        Org entity = GenericBuilder.of(Org::new)
                .set(Org::setId, 11L)
                .set(Org::setCode, "ORG_CODE_11")
                .set(Org::setName, "ORG NAME 11")
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        Org entity = GenericBuilder.of(Org::new)
                .set(Org::setId, 1L)
                .set(Org::setCode, "ORG_CODE_12")
                .set(Org::setName, "ORG NAME 12")
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "code", "name", "superId")
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
