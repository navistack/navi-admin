package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.dtobj.DictionaryItemDo;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictionaryItemDao {
    List<DictionaryItemDo> selectAll();

    List<DictionaryItemDo> selectAllByDictionaryId(Long dictionaryId);

    long countByQuery(DictionaryItemQuery query);

    List<DictionaryItemDo> paginateByQuery(DictionaryItemQuery query, Pageable pageable);

    DictionaryItemDo selectById(Long id);

    Long selectIdByCodeAndDictionaryId(String code, Long dictionaryId);

    boolean existsById(Long id);

    boolean existsByDictionaryId(Long dictionaryId);

    int insert(DictionaryItemDo dtObj);

    int updateById(DictionaryItemDo dtObj);

    int deleteById(Long id);
}
