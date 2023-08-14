package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.dtobj.DictionaryDo;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface DictionaryDao {
    List<DictionaryDo> selectAll();

    long countByQuery(DictionaryQuery query);

    List<DictionaryDo> paginateByQuery(DictionaryQuery query, Pageable pageable);

    DictionaryDo selectById(Long id);

    DictionaryDo selectByCode(String code);

    Long selectIdByCode(String code);

    boolean existsById(Long id);

    int insert(DictionaryDo dtObj);

    int updateById(DictionaryDo dtObj);

    int deleteById(Long id);
}
