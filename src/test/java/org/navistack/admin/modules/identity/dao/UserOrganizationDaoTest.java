package org.navistack.admin.modules.identity.dao;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.navistack.admin.modules.identity.dtobj.UserOrganizationDo;
import org.navistack.boot.testsupport.testcontainers.MysqlContainer;
import org.navistack.framework.utils.GenericBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
    void selectById_shouldWorkAsExpected() {
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(
                        GenericBuilder.of(UserOrganizationDo::new).set(UserOrganizationDo::setId, 1L).set(UserOrganizationDo::setOrganizationId, 1L).set(UserOrganizationDo::setUserId, 1L).build()
                );
    }

    @Test
    @Transactional
    void insert_shouldWorkAsExpected() {
        UserOrganizationDo dtObj = GenericBuilder.of(UserOrganizationDo::new)
                .set(UserOrganizationDo::setOrganizationId, 3L)
                .set(UserOrganizationDo::setUserId, 1L)
                .build();
        assertThat(dao.insert(dtObj)).isEqualTo(1);
        assertThat(dtObj.getId()).isNotNull();
        assertThat(dao.selectById(dtObj.getId()))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
                .isEqualTo(dtObj);
    }

    @Test
    @Transactional
    void updateById_shouldWorkAsExpected() {
        UserOrganizationDo dtObj = GenericBuilder.of(UserOrganizationDo::new)
                .set(UserOrganizationDo::setId, 1L)
                .set(UserOrganizationDo::setOrganizationId, 3L)
                .set(UserOrganizationDo::setUserId, 2L)
                .build();
        assertThat(dao.updateById(dtObj)).isEqualTo(1);
        assertThat(dao.selectById(1L))
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "organizationId", "userId")
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
