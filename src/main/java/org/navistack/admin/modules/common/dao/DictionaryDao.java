package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictionaryDao {
    List<Dictionary> selectAll();

    List<Dictionary> select(DictionaryQuery query);

    long count(DictionaryQuery query);

    List<Dictionary> selectWithPageable(DictionaryQuery query, Pageable pageable);

    Dictionary selectOne(DictionaryQuery query);

    Dictionary selectOneById(Long id);

    int insert(Dictionary entity);

    int updateById(Dictionary entity);

    int deleteById(Long id);
}
