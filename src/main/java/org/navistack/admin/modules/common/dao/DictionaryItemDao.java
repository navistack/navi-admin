package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictionaryItemDao {
    List<DictionaryItem> selectAll();

    List<DictionaryItem> select(DictionaryItemQuery query);

    long count(DictionaryItemQuery query);

    List<DictionaryItem> selectWithPageable(DictionaryItemQuery query, Pageable pageable);

    DictionaryItem selectOne(DictionaryItemQuery query);

    DictionaryItem selectOneById(Long id);

    int insert(DictionaryItem entity);

    int updateById(DictionaryItem entity);

    int deleteById(Long id);
}
