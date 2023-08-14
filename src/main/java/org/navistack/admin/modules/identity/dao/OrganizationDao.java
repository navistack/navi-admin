package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.dtobj.OrganizationDo;
import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface OrganizationDao {
    long countByQuery(OrganizationQuery query);

    List<OrganizationDo> paginateByQuery(OrganizationQuery query, Pageable pageable);

    OrganizationDo selectById(Long id);

    Long selectIdByCode(String code);

    boolean existsById(Long id);

    boolean existsBySuperId(Long superId);

    int insert(OrganizationDo dtObj);

    int updateById(OrganizationDo dtObj);

    int deleteById(Long id);
}
