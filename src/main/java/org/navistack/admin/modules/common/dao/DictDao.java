package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.common.query.DictQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictDao {
    List<Dict> selectAll();

    List<Dict> select(DictQuery query);

    long count(DictQuery query);

    List<Dict> selectWithPageable(DictQuery query, Pageable pageable);

    Dict selectOne(DictQuery query);

    Dict selectOneById(Long id);

    int insert(Dict entity);

    int updateById(Dict entity);

    int deleteById(Long id);
}
