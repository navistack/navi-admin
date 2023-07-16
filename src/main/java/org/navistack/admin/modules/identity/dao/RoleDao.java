package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.Role;
import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface RoleDao {

    List<Role> selectAllByQuery(RoleQuery query);

    boolean existsByQuery(RoleQuery query);

    long countByQuery(RoleQuery query);

    List<Role> paginateByQuery(RoleQuery query, Pageable pageable);

    Role selectByQuery(RoleQuery query);

    Role selectById(Long id);

    Long selectIdByCode(String code);

    List<Long> selectAllIdsByIds(List<Long> ids);

    boolean existsById(Long id);

    int insert(Role entity);

    int updateById(Role entity);

    int deleteById(Long id);
}
