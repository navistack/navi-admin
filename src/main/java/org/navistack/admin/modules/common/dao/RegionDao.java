package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface RegionDao {
    List<Region> selectAll();

    List<Region> select(RegionQuery query);

    List<Region> selectRecursively(RegionQuery query);

    long count(RegionQuery query);

    List<Region> selectWithPageable(RegionQuery query, Pageable pageable);

    Region selectOne(RegionQuery query);

    Region selectOneById(Long id);

    int insert(Region entity);

    int updateById(Region entity);

    int deleteById(Long id);
}
