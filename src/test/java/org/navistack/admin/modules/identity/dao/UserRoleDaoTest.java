package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.entity.UserRole;
import org.navistack.admin.modules.identity.query.UserRoleQuery;
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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data/identity/user_role.sql")
class UserRoleDaoTest {
    @Container
    static MysqlContainer mysql = new MysqlContainer();

    @Setter(onMethod = @__(@Autowired))
    private UserRoleDao dao;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void selectAllByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectAllByQuery(UserRoleQuery.builder().id(1L).build()))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactly(
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 1L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 1L).build()
                );
        assertThat(dao.selectAllByQuery(UserRoleQuery.builder().roleId(1L).build()))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 1L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 1L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 2L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 2L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 3L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 3L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 4L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 4L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 5L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 5L).build()
                );
        assertThat(dao.selectAllByQuery(UserRoleQuery.builder().userId(4L).build()))
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 4L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 4L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 6L).set(UserRole::setRoleId, 2L).set(UserRole::setUserId, 4L).build()
                );
    }

    @Test
    void existsByQuery_shouldWorkAsExpected() {
        assertThat(dao.existsByQuery(UserRoleQuery.builder().id(1L).build())).isTrue();
        assertThat(dao.existsByQuery(UserRoleQuery.builder().roleId(1L).build())).isTrue();
        assertThat(dao.existsByQuery(UserRoleQuery.builder().userId(4L).build())).isTrue();

        assertThat(dao.existsByQuery(UserRoleQuery.builder().id(100L).build())).isFalse();
        assertThat(dao.existsByQuery(UserRoleQuery.builder().roleId(100L).build())).isFalse();
        assertThat(dao.existsByQuery(UserRoleQuery.builder().userId(100L).build())).isFalse();
    }

    @Test
    void countByQuery_shouldWorkAsExpected() {
        assertThat(dao.countByQuery(UserRoleQuery.builder().id(1L).build())).isEqualTo(1L);
        assertThat(dao.countByQuery(UserRoleQuery.builder().roleId(1L).build())).isEqualTo(5L);
        assertThat(dao.countByQuery(UserRoleQuery.builder().userId(4L).build())).isEqualTo(2L);
    }

    @Test
    void paginateByQuery_shouldWorkAsExpected() {
        UserRoleQuery query = UserRoleQuery.builder()
                .build();
        PageRequest pageRequest = GenericBuilder.of(PageRequest::new)
                .set(PageRequest::setPageNumber, 1)
                .set(PageRequest::setPageSize, 5)
                .set(PageRequest::setSort, Sort.by(Sort.Direction.DESC, "id"))
                .build();
        assertThat(dao.paginateByQuery(query, pageRequest))
                .hasSize(5)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "roleId", "privilegeId")
                .containsExactlyInAnyOrder(
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 6L).set(UserRole::setRoleId, 2L).set(UserRole::setUserId, 4L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 7L).set(UserRole::setRoleId, 2L).set(UserRole::setUserId, 5L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 8L).set(UserRole::setRoleId, 2L).set(UserRole::setUserId, 6L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 9L).set(UserRole::setRoleId, 2L).set(UserRole::setUserId, 7L).build(),
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 10L).set(UserRole::setRoleId, 2L).set(UserRole::setUserId, 8L).build()
                ).isSortedAccordingTo(Comparator.comparingLong(UserRole::getId).reversed());
    }

    @Test
    void selectByQuery_shouldWorkAsExpected() {
        assertThat(dao.selectByQuery(UserRoleQuery.builder().id(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 1L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 1L).build()
                );
        assertThat(dao.selectByQuery(UserRoleQuery.builder().roleId(1L).userId(1L).build()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 1L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 1L).build()
                );
    }

    @Test
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(
                        GenericBuilder.of(UserRole::new).set(UserRole::setId, 1L).set(UserRole::setRoleId, 1L).set(UserRole::setUserId, 1L).build()
                );
    }

    @Test
    void existsByRoleId_shouldWorkAsExpected() {
        assertThat(dao.existsByRoleId(1L)).isTrue();
        assertThat(dao.existsByRoleId(100L)).isFalse();
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        UserRole entity = GenericBuilder.of(UserRole::new)
                .set(UserRole::setId, 11L)
                .set(UserRole::setRoleId, 3L)
                .set(UserRole::setUserId, 1L)
                .build();
        assertThat(dao.insert(entity)).isEqualTo(1);
        assertThat(dao.selectById(11L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
                .isEqualTo(entity);
    }

    @Test
    @Transactional
    void insertAll_shouldWorkAsExpected() {
        List<UserRole> entities = Arrays.asList(
                GenericBuilder.of(UserRole::new)
                        .set(UserRole::setId, 11L)
                        .set(UserRole::setRoleId, 3L)
                        .set(UserRole::setUserId, 1L)
                        .build(),
                GenericBuilder.of(UserRole::new)
                        .set(UserRole::setId, 12L)
                        .set(UserRole::setRoleId, 3L)
                        .set(UserRole::setUserId, 2L)
                        .build()
        );
        assertThat(dao.insertAll(entities)).isEqualTo(2);
        for (UserRole entity : entities) {
            assertThat(dao.selectById(entity.getId()))
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "roleId", "privilegeId")
                    .isEqualTo(entity);
        }
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        UserRole entity = GenericBuilder.of(UserRole::new)
                .set(UserRole::setId, 1L)
                .set(UserRole::setRoleId, 3L)
                .set(UserRole::setUserId, 2L)
                .build();
        assertThat(dao.updateById(entity)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "roleId", "privilegeId")
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
        assertThat(dao.deleteAllByQuery(UserRoleQuery.builder().roleId(1L).build())).isEqualTo(5);
        assertThat(dao.deleteAllByQuery(UserRoleQuery.builder().userId(6L).build())).isEqualTo(1);
    }

    @Test
    @Transactional
    void deleteAllByUserId_shouldWorkAsExpected() {
        assertThat(dao.deleteAllByUserId(5L)).isEqualTo(2);
    }
}
