package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictionaryItemDao {
    List<DictionaryItem> selectAll();

    List<DictionaryItem> selectAllByQuery(DictionaryItemQuery query);

    List<DictionaryItem> selectAllByDictionaryId(Long dictionaryId);

    boolean existsByQuery(DictionaryItemQuery query);

    long countByQuery(DictionaryItemQuery query);

    List<DictionaryItem> paginateByQuery(DictionaryItemQuery query, Pageable pageable);

    DictionaryItem selectByQuery(DictionaryItemQuery query);

    DictionaryItem selectById(Long id);

    Long selectIdByCodeAndDictionaryId(String code, Long dictionaryId);

    boolean existsById(Long id);

    boolean existsByDictionaryId(Long dictionaryId);

    int insert(DictionaryItem entity);

    int updateById(DictionaryItem entity);

    int deleteById(Long id);
}
