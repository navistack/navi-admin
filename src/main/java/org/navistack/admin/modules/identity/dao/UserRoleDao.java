package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.dtobj.UserRoleDo;

import java.util.List;

@Mapper
public interface UserRoleDao {
    List<Long> selectAllRoleIdsByUserId(Long userId);

    UserRoleDo selectById(Long id);

    boolean existsByRoleId(Long roleId);

    int insert(UserRoleDo dtObj);

    int insertAll(List<UserRoleDo> dtObjs);

    int updateById(UserRoleDo dtObj);

    int deleteById(Long id);

    int deleteAllByUserId(Long userId);
}
