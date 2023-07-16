package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.User;
import org.navistack.admin.modules.identity.query.UserLoginNameQuery;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> selectAllByQuery(UserQuery query);

    boolean existsByQuery(UserQuery query);

    long countByQuery(UserQuery query);

    List<User> paginateByQuery(UserQuery query, Pageable pageable);

    User selectByQuery(UserQuery query);

    User selectByLoginName(UserLoginNameQuery query);

    User selectById(Long id);

    Long selectIdByLoginName(String loginName);

    Long selectIdByMobileNumber(String mobileNumber);

    Long selectIdByEmailAddress(String emailAddress);

    boolean existsById(Long id);

    int insert(User entity);

    int updateById(User entity);

    int deleteById(Long id);
}
