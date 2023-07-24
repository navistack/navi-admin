package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.UserRole;
import org.navistack.admin.modules.identity.query.UserRoleQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserRoleDao {

    List<UserRole> selectAllByQuery(UserRoleQuery query);

    List<Long> selectAllRoleIdsByUserId(Long userId);

    boolean existsByQuery(UserRoleQuery query);

    long countByQuery(UserRoleQuery query);

    List<UserRole> paginateByQuery(UserRoleQuery query, Pageable pageable);

    UserRole selectByQuery(UserRoleQuery query);

    UserRole selectById(Long id);

    boolean existsByRoleId(Long roleId);

    int insert(UserRole entity);

    int insertAll(List<UserRole> entities);

    int updateById(UserRole entity);

    int deleteById(Long id);

    int deleteAllByQuery(UserRoleQuery query);

    int deleteAllByUserId(Long userId);
}
