package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.UserOrg;
import org.navistack.admin.modules.common.query.UserOrgQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserOrgDao {

    List<UserOrg> select(UserOrgQuery query);

    long count(UserOrgQuery query);

    List<UserOrg> selectWithPageable(UserOrgQuery query, Pageable pageable);

    UserOrg selectOne(UserOrgQuery query);

    UserOrg selectOneById(Long id);

    int insert(UserOrg entity);

    int updateById(UserOrg entity);

    int deleteById(Long id);

    int delete(UserOrgQuery query);
}
