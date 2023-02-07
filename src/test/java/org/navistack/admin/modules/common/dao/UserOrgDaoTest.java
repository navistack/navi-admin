package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.UserOrg;
import org.navistack.admin.modules.common.query.UserOrgQuery;
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
@Sql(scripts = "classpath:data/user_org.sql")
class UserOrgDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private UserOrgDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void testSelect() {
        assertThat(dao.select(UserOrgQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactly(
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 1L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 1L).build()
                );
        assertThat(dao.select(UserOrgQuery.builder().orgId(1L).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 1L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 1L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 2L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 2L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 3L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 3L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 4L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 5L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 5L).build()
                );
        assertThat(dao.select(UserOrgQuery.builder().userId(4L).build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 4L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 6L).set(UserOrg::setOrgId, 2L).set(UserOrg::setUserId, 4L).build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(UserOrgQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(UserOrgQuery.builder().orgId(1L).build())).isEqualTo(5L);
        assertThat(dao.count(UserOrgQuery.builder().userId(4L).build())).isEqualTo(2L);
    }

    @Test
    void testSelectWithPageable() {
        UserOrgQuery query = UserOrgQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 6L).set(UserOrg::setOrgId, 2L).set(UserOrg::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 7L).set(UserOrg::setOrgId, 2L).set(UserOrg::setUserId, 5L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 8L).set(UserOrg::setOrgId, 2L).set(UserOrg::setUserId, 6L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 9L).set(UserOrg::setOrgId, 2L).set(UserOrg::setUserId, 7L).build(),
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 10L).set(UserOrg::setOrgId, 2L).set(UserOrg::setUserId, 8L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(UserOrg::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(UserOrgQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 1L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 1L).build()
                );
        assertThat(dao.selectOne(UserOrgQuery.builder().orgId(1L).userId(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 1L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 1L).build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(UserOrg::new).set(UserOrg::setId, 1L).set(UserOrg::setOrgId, 1L).set(UserOrg::setUserId, 1L).build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        UserOrg entity = GenericBuilder.of(UserOrg::new)
                .set(UserOrg::setId, 11L)
                .set(UserOrg::setOrgId, 3L)
                .set(UserOrg::setUserId, 1L)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        UserOrg entity = GenericBuilder.of(UserOrg::new)
                .set(UserOrg::setId, 1L)
                .set(UserOrg::setOrgId, 3L)
                .set(UserOrg::setUserId, 2L)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testDeleteById() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectOneById(2L))
                .isNull();
    }

    @Test
    void testDelete() {
        assertThat(dao.delete(UserOrgQuery.builder().orgId(1L).build())).isEqualTo(5);
        assertThat(dao.delete(UserOrgQuery.builder().userId(6L).build())).isEqualTo(1);
    }
}
