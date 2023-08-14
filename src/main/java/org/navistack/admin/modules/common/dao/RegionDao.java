package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.dtobj.RegionDo;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface RegionDao {
    List<RegionDo> selectAll();

    List<RegionDo> selectAllHierarchicalByCode(String code);

    List<RegionDo> selectAllByParentCode(String parentCode);

    long countByQuery(RegionQuery query);

    List<RegionDo> paginateByQuery(RegionQuery query, Pageable pageable);

    RegionDo selectById(Long id);

    Long selectIdByCode(String code);

    String selectCodeById(Long id);

    boolean existsById(Long id);

    boolean existsByCode(String code);

    boolean existsByParentCode(String regionCode);

    int insert(RegionDo dtObj);

    int updateById(RegionDo dtObj);

    int deleteById(Long id);
}
