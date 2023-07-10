package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.framework.data.Pageable;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PrivilegeDao {

    List<Privilege> select(PrivilegeQuery query);

    List<Privilege> selectByIds(Collection<Long> ids);

    long count(PrivilegeQuery query);

    List<Privilege> selectWithPageable(PrivilegeQuery query, Pageable pageable);

    Privilege selectOne(PrivilegeQuery query);

    Privilege selectOneById(Long id);

    int insert(Privilege entity);

    int updateById(Privilege entity);

    int deleteById(Long id);
}
