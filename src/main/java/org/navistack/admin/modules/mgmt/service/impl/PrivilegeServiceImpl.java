package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.common.query.PrivilegeQuery;
import org.navistack.admin.modules.mgmt.service.PrivilegeService;
import org.navistack.admin.modules.mgmt.service.convert.PrivilegeConverter;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeDao dao;

    public PrivilegeServiceImpl(PrivilegeDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<PrivilegeDto> paginate(PrivilegeQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<Privilege> entities = dao.selectWithPageable(query, pageable);
        Collection<PrivilegeDto> dtos = PrivilegeConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(PrivilegeDto dto) {
        ensureUnique(dto);

        Privilege entity = PrivilegeConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(PrivilegeDto dto) {
        ensureUnique(dto);

        Privilege entity = PrivilegeConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }

    protected void ensureUnique(PrivilegeDto dto) {
        PrivilegeQuery queryDto = PrivilegeQuery.builder()
                .code(dto.getCode())
                .build();
        Privilege existedOne = dao.selectOne(queryDto);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Privilege has existed");
    }
}
