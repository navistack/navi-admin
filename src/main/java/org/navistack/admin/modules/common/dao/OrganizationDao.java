package org.navistack.admin.modules.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.common.entity.Organization;
import org.navistack.admin.modules.common.query.OrganizationQuery;
import org.navistack.framework.data.Pageable;

import java.util.List;

@Mapper
public interface OrganizationDao {

    List<Organization> select(OrganizationQuery query);

    long count(OrganizationQuery query);

    List<Organization> selectWithPageable(OrganizationQuery query, Pageable pageable);

    Organization selectOne(OrganizationQuery query);

    Organization selectOneById(Long id);

    int insert(Organization entity);

    int updateById(Organization entity);

    int deleteById(Long id);
}
