package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.entity.Organization;
import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface OrganizationDao {

    List<Organization> selectAllByQuery(OrganizationQuery query);

    boolean existsByQuery(OrganizationQuery query);

    long countByQuery(OrganizationQuery query);

    List<Organization> paginateByQuery(OrganizationQuery query, Pageable pageable);

    Organization selectByQuery(OrganizationQuery query);

    Organization selectById(Long id);

    Long selectIdByCode(String code);

    boolean existsById(Long id);

    boolean existsBySuperId(Long superId);

    int insert(Organization entity);

    int updateById(Organization entity);

    int deleteById(Long id);
}
