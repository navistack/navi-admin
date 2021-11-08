package org.navistack.admin.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.navistack.admin.modules.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
