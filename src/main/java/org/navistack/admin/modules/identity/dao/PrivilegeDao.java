package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.framework.data.Pageable;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PrivilegeDao {

    List<Privilege> selectAllByQuery(PrivilegeQuery query);

    List<Privilege> selectAllByIds(Collection<Long> ids);

    boolean existsByQuery(PrivilegeQuery query);

    long countByQuery(PrivilegeQuery query);

    List<Privilege> paginateByQuery(PrivilegeQuery query, Pageable pageable);

    Privilege selectByQuery(PrivilegeQuery query);

    Privilege selectById(Long id);

    Long selectIdByCode(String code);

    List<Long> selectAllIdsByIds(List<Long> ids);

    boolean existsById(Long id);

    boolean existsByParentId(Long id);

    int insert(Privilege entity);

    int updateById(Privilege entity);

    int deleteById(Long id);
}
