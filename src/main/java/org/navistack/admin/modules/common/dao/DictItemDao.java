package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.common.query.DictItemQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictItemDao {
    List<DictItem> selectAll();

    List<DictItem> select(DictItemQuery query);

    long count(DictItemQuery query);

    List<DictItem> selectWithPageable(DictItemQuery query, Pageable pageable);

    DictItem selectOne(DictItemQuery query);

    DictItem selectOneById(Long id);

    int insert(DictItem entity);

    int updateById(DictItem entity);

    int deleteById(Long id);
}
