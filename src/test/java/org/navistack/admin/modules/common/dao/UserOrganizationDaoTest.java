package org.navistack.admin.modules.common.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.common.entity.UserOrganization;
import org.navistack.admin.modules.common.query.UserOrganizationQuery;
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
@Sql(scripts = "classpath:data/user_organization.sql")
class UserOrganizationDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private UserOrganizationDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void testSelect() {
        assertThat(dao.select(UserOrganizationQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "organizationId", "userId")
                .containsExactly(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
        assertThat(dao.select(UserOrganizationQuery.builder().organizationId(1L).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "organizationId", "userId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 2L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 2L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 3L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 3L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 4L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 5L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 5L).build()
                );
        assertThat(dao.select(UserOrganizationQuery.builder().userId(4L).build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "organizationId", "userId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 4L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 6L).set(UserOrganization::setOrganizationId, 2L).set(UserOrganization::setUserId, 4L).build()
                );
    }

    @Test
    void testCount() {
        assertThat(dao.count(UserOrganizationQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.count(UserOrganizationQuery.builder().organizationId(1L).build())).isEqualTo(5L);
        assertThat(dao.count(UserOrganizationQuery.builder().userId(4L).build())).isEqualTo(2L);
    }

    @Test
    void testSelectWithPageable() {
        UserOrganizationQuery query = UserOrganizationQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.selectWithPageable(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "organizationId", "userId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 6L).set(UserOrganization::setOrganizationId, 2L).set(UserOrganization::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 7L).set(UserOrganization::setOrganizationId, 2L).set(UserOrganization::setUserId, 5L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 8L).set(UserOrganization::setOrganizationId, 2L).set(UserOrganization::setUserId, 6L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 9L).set(UserOrganization::setOrganizationId, 2L).set(UserOrganization::setUserId, 7L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 10L).set(UserOrganization::setOrganizationId, 2L).set(UserOrganization::setUserId, 8L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(UserOrganization::getId).reversed());
    }

    @Test
    void testSelectOne() {
        assertThat(dao.selectOne(UserOrganizationQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
        assertThat(dao.selectOne(UserOrganizationQuery.builder().organizationId(1L).userId(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
    }

    @Test
    void testSelectOneById() {
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
    }

    @Test
    @Transactional
    void testInsert() {
        UserOrganization entity = GenericBuilder.of(UserOrganization::new)
                .set(UserOrganization::setId, 11L)
                .set(UserOrganization::setOrganizationId, 3L)
                .set(UserOrganization::setUserId, 1L)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void testUpdateById() {
        UserOrganization entity = GenericBuilder.of(UserOrganization::new)
                .set(UserOrganization::setId, 1L)
                .set(UserOrganization::setOrganizationId, 3L)
                .set(UserOrganization::setUserId, 2L)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectOneById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
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
        assertThat(dao.delete(UserOrganizationQuery.builder().organizationId(1L).build())).isEqualTo(5);
        assertThat(dao.delete(UserOrganizationQuery.builder().userId(6L).build())).isEqualTo(1);
    }
}
