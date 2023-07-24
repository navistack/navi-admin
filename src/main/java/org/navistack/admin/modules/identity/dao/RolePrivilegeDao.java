package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.RolePrivilege;
import org.navistack.admin.modules.identity.query.RolePrivilegeQuery;
import org.navistack.framework.data.Pageable;

import java.util.Collection;
import java.util.List;

@Mapper
public interface RolePrivilegeDao {

    List<RolePrivilege> selectAllByQuery(RolePrivilegeQuery query);

    List<RolePrivilege> selectAllByRoleIds(Collection<Long> roleIds);

    List<Long> selectAllPrivilegeIdsByRoleId(Long roleId);

    List<Long> selectAllPrivilegeIdsByRoleIds(List<Long> roleIds);

    boolean existsByQuery(RolePrivilegeQuery query);

    long countByQuery(RolePrivilegeQuery query);

    List<RolePrivilege> paginateByQuery(RolePrivilegeQuery query, Pageable pageable);

    RolePrivilege selectByQuery(RolePrivilegeQuery query);

    RolePrivilege selectById(Long id);

    int insert(RolePrivilege entity);

    int insertAll(List<RolePrivilege> entities);

    int updateById(RolePrivilege entity);

    int deleteById(Long id);

    int deleteAllByQuery(RolePrivilegeQuery query);

    int deleteAllByRoleId(Long roleId);
}
