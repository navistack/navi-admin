package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Org;
import org.navistack.admin.modules.common.query.OrgQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface OrgDao {

    List<Org> select(OrgQuery query);

    long count(OrgQuery query);

    List<Org> selectWithPageable(OrgQuery query, Pageable pageable);

    Org selectOne(OrgQuery query);

    Org selectOneById(Long id);

    int insert(Org entity);

    int updateById(Org entity);

    int deleteById(Long id);
}
