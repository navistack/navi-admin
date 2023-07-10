package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.RolePrivilege;
import org.navistack.admin.modules.identity.query.RolePrivilegeQuery;
import org.navistack.framework.data.Pageable;

import java.util.Collection;
import java.util.List;

@Mapper
public interface RolePrivilegeDao {

    List<RolePrivilege> select(RolePrivilegeQuery query);

    List<RolePrivilege> selectByRoleIdIn(Collection<Long> roleIds);

    long count(RolePrivilegeQuery query);

    List<RolePrivilege> selectWithPageable(RolePrivilegeQuery query, Pageable pageable);

    RolePrivilege selectOne(RolePrivilegeQuery query);

    RolePrivilege selectOneById(Long id);

    int insert(RolePrivilege entity);

    int updateById(RolePrivilege entity);

    int deleteById(Long id);

    int delete(RolePrivilegeQuery query);
}
