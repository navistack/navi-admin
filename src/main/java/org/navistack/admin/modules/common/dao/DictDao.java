package org.navistack.admin.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Dict;

@Mapper
public interface DictDao extends BaseMapper<Dict> {
}
