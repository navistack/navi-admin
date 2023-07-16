package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictionaryDao {
    List<Dictionary> selectAll();

    List<Dictionary> selectAllByQuery(DictionaryQuery query);

    boolean existsByQuery(DictionaryQuery query);

    long countByQuery(DictionaryQuery query);

    List<Dictionary> paginateByQuery(DictionaryQuery query, Pageable pageable);

    Dictionary selectByQuery(DictionaryQuery query);

    Dictionary selectById(Long id);

    Long selectIdByCode(String code);

    boolean existsById(Long id);

    int insert(Dictionary entity);

    int updateById(Dictionary entity);

    int deleteById(Long id);
}
