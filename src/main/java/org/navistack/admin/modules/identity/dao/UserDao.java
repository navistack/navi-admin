package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.query.UserLoginNameQuery;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> select(UserQuery query);

    long count(UserQuery query);

    List<User> selectWithPageable(UserQuery query, Pageable pageable);

    User selectOne(UserQuery query);

    User selectOneByLoginName(UserLoginNameQuery query);

    User selectOneById(Long id);

    int insert(User entity);

    int updateById(User entity);

    int deleteById(Long id);
}
