package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Role;
import org.navistack.admin.modules.common.query.RoleQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface RoleDao {

    List<Role> select(RoleQuery query);

    long count(RoleQuery query);

    List<Role> selectWithPageable(RoleQuery query, Pageable pageable);

    Role selectOne(RoleQuery query);

    Role selectOneById(Long id);

    int insert(Role entity);

    int updateById(Role entity);

    int deleteById(Long id);
}
