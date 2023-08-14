package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.dtobj.RoleDo;
import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface RoleDao {
    long countByQuery(RoleQuery query);

    List<RoleDo> paginateByQuery(RoleQuery query, Pageable pageable);

    RoleDo selectById(Long id);

    Long selectIdByCode(String code);

    List<Long> selectAllIdsByIds(List<Long> ids);

    boolean existsById(Long id);

    int insert(RoleDo dtObj);

    int updateById(RoleDo dtObj);

    int deleteById(Long id);
}
