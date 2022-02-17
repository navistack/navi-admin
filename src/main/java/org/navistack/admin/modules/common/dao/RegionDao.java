package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.framework.mybatisplus.CrudMapper;

@Mapper
public interface RegionDao extends CrudMapper<Region> {
}
