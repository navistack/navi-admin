package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.UserOrganization;
import org.navistack.admin.modules.common.query.UserOrganizationQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserOrganizationDao {

    List<UserOrganization> select(UserOrganizationQuery query);

    long count(UserOrganizationQuery query);

    List<UserOrganization> selectWithPageable(UserOrganizationQuery query, Pageable pageable);

    UserOrganization selectOne(UserOrganizationQuery query);

    UserOrganization selectOneById(Long id);

    int insert(UserOrganization entity);

    int updateById(UserOrganization entity);

    int deleteById(Long id);

    int delete(UserOrganizationQuery query);
}
