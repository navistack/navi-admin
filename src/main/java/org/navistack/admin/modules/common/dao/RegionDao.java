package org.navistack.admin.modules.common.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.framework.mybatisplusplus.CrudMapper;

import java.util.List;

@Mapper
public interface RegionDao extends CrudMapper<Region> {
    List<Region> selectListRecursively(@Param(Constants.WRAPPER) Wrapper<Region> queryWrapper);
}
