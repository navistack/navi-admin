package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.UserRole;
import org.navistack.admin.modules.identity.query.UserRoleQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserRoleDao {

    List<UserRole> select(UserRoleQuery query);

    long count(UserRoleQuery query);

    List<UserRole> selectWithPageable(UserRoleQuery query, Pageable pageable);

    UserRole selectOne(UserRoleQuery query);

    UserRole selectOneById(Long id);

    int insert(UserRole entity);

    int updateById(UserRole entity);

    int deleteById(Long id);

    int delete(UserRoleQuery query);
}
