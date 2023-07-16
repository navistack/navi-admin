package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.UserOrganization;
import org.navistack.admin.modules.identity.query.UserOrganizationQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserOrganizationDao {

    List<UserOrganization> selectAllByQuery(UserOrganizationQuery query);

    boolean existsByQuery(UserOrganizationQuery query);

    long countByQuery(UserOrganizationQuery query);

    List<UserOrganization> paginateByQuery(UserOrganizationQuery query, Pageable pageable);

    UserOrganization selectByQuery(UserOrganizationQuery query);

    UserOrganization selectById(Long id);

    int insert(UserOrganization entity);

    int updateById(UserOrganization entity);

    int deleteById(Long id);

    int deleteAllByQuery(UserOrganizationQuery query);
}
