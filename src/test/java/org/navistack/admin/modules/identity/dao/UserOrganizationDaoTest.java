package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.entity.UserOrganization;
import org.navistack.admin.modules.identity.query.UserOrganizationQuery;
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
@Sql(scripts = "classpath:data/identity/user_organization.sql")
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
    void selectAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQuery(UserOrganizationQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "organizationId", "userId")
                .containsExactly(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
        assertThat(dao.selectAllByQuery(UserOrganizationQuery.builder().organizationId(1L).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "organizationId", "userId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 2L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 2L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 3L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 3L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 4L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 5L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 5L).build()
                );
        assertThat(dao.selectAllByQuery(UserOrganizationQuery.builder().userId(4L).build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "organizationId", "userId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 4L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 4L).build(),
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 6L).set(UserOrganization::setOrganizationId, 2L).set(UserOrganization::setUserId, 4L).build()
                );
    }

    @Test
    void existsByQuery_shouldWorkAsExpected() {
        assertThat(dao.existsByQuery(UserOrganizationQuery.builder().id(1L).build())).isTrue();
        assertThat(dao.existsByQuery(UserOrganizationQuery.builder().organizationId(1L).build())).isTrue();
        assertThat(dao.existsByQuery(UserOrganizationQuery.builder().userId(4L).build())).isTrue();

        assertThat(dao.existsByQuery(UserOrganizationQuery.builder().id(100L).build())).isFalse();
        assertThat(dao.existsByQuery(UserOrganizationQuery.builder().organizationId(100L).build())).isFalse();
        assertThat(dao.existsByQuery(UserOrganizationQuery.builder().userId(100L).build())).isFalse();
    }

    @Test
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(UserOrganizationQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(UserOrganizationQuery.builder().organizationId(1L).build())).isEqualTo(5L);
        assertThat(dao.countByQuery(UserOrganizationQuery.builder().userId(4L).build())).isEqualTo(2L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        UserOrganizationQuery query = UserOrganizationQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest))
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
    void selectByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectByQuery(UserOrganizationQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
        assertThat(dao.selectByQuery(UserOrganizationQuery.builder().organizationId(1L).userId(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(
                        GenericBuilder.of(UserOrganization::new).set(UserOrganization::setId, 1L).set(UserOrganization::setOrganizationId, 1L).set(UserOrganization::setUserId, 1L).build()
                );
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        UserOrganization entity = GenericBuilder.of(UserOrganization::new)
                .set(UserOrganization::setOrganizationId, 3L)
                .set(UserOrganization::setUserId, 1L)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(entity.getId()).isNotNull();
        assertThat(dao.selectById(entity.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        UserOrganization entity = GenericBuilder.of(UserOrganization::new)
                .set(UserOrganization::setId, 1L)
                .set(UserOrganization::setOrganizationId, 3L)
                .set(UserOrganization::setUserId, 2L)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void deleteById_shouldWorkAsExpected() {
        assertThat(dao.deleteById(2L)).isEqualTo(1);
        assertThat(dao.selectById(2L))
                .isNull();
    }

    @Test
    @Transactional
    void deleteAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.deleteAllByQuery(UserOrganizationQuery.builder().organizationId(1L).build())).isEqualTo(5);
        assertThat(dao.deleteAllByQuery(UserOrganizationQuery.builder().userId(6L).build())).isEqualTo(1);
    }
}
