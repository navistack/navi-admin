package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface RegionDao {
    List<Region> selectAll();

    List<Region> selectAllByQuery(RegionQuery query);

    List<Region> selectAllByQueryRecursively(RegionQuery query);

    boolean existsByQuery(RegionQuery query);

    long countByQuery(RegionQuery query);

    List<Region> paginateByQuery(RegionQuery query, Pageable pageable);

    Region selectByQuery(RegionQuery query);

    Region selectById(Long id);

    Long selectIdByCode(String code);

    String selectCodeById(Long id);

    boolean existsById(Long id);

    boolean existsByCode(String code);

    boolean existsByParentCode(String regionCode);

    int insert(Region entity);

    int updateById(Region entity);

    int deleteById(Long id);
}
