package org.navistack.admin.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Region;

@Mapper
public interface RegionDao extends BaseMapper<Region> {
}
