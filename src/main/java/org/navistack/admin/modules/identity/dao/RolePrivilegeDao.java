package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.dtobj.RolePrivilegeDo;

import java.util.Collection;
import java.util.List;

@Mapper
public interface RolePrivilegeDao {
    List<RolePrivilegeDo> selectAllByRoleIds(Collection<Long> roleIds);

    List<Long> selectAllPrivilegeIdsByRoleId(Long roleId);

    List<Long> selectAllPrivilegeIdsByRoleIds(List<Long> roleIds);

    RolePrivilegeDo selectById(Long id);

    int insert(RolePrivilegeDo dtObj);

    int insertAll(List<RolePrivilegeDo> dtObjs);

    int updateById(RolePrivilegeDo dtObj);

    int deleteById(Long id);

    int deleteAllByRoleId(Long roleId);
}
