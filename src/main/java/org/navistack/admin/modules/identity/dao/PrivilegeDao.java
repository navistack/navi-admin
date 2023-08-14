package org.navistack.admin.modules.identity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.navistack.admin.modules.identity.dtobj.PrivilegeDo;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.framework.data.Pageable;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PrivilegeDao {
    List<PrivilegeDo> selectAllByIds(Collection<Long> ids);

    long countByQuery(PrivilegeQuery query);

    List<PrivilegeDo> paginateByQuery(PrivilegeQuery query, Pageable pageable);

    PrivilegeDo selectById(Long id);

    Long selectIdByCode(String code);

    List<Long> selectAllIdsByIds(List<Long> ids);

    boolean existsById(Long id);

    boolean existsByParentId(Long id);

    int insert(PrivilegeDo dtObj);

    int updateById(PrivilegeDo dtObj);

    int deleteById(Long id);
}
