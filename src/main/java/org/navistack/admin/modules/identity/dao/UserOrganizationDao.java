package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.dtobj.UserOrganizationDo;

@Mapper
public interface UserOrganizationDao {
    UserOrganizationDo selectById(Long id);

    int insert(UserOrganizationDo dtObj);

    int updateById(UserOrganizationDo dtObj);

    int deleteById(Long id);
}
