package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.framework.mybatisplus.CrudMapper;

@Mapper
public interface UserDao extends CrudMapper<User> {
}
