package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.framework.mybatisplusplus.CrudMapper;

@Mapper
public interface DictItemDao extends CrudMapper<DictItem> {
}
