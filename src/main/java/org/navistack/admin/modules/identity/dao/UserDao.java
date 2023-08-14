package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.dtobj.UserDo;
import org.navistack.admin.modules.identity.query.UserLoginNameQuery;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface UserDao {
    long countByQuery(UserQuery query);

    List<UserDo> paginateByQuery(UserQuery query, Pageable pageable);

    UserDo selectByLoginName(UserLoginNameQuery query);

    UserDo selectById(Long id);

    Long selectIdByLoginName(String loginName);

    Long selectIdByMobileNumber(String mobileNumber);

    Long selectIdByEmailAddress(String emailAddress);

    boolean existsById(Long id);

    int insert(UserDo dtObj);

    int updateById(UserDo dtObj);

    int deleteById(Long id);
}
