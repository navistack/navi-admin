package org.navistack.admin.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Org;

@Mapper
public interface OrgDao extends BaseMapper<Org> {
}
