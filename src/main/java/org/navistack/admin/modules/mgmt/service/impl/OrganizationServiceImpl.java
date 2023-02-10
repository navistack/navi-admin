package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.OrganizationDao;
import org.navistack.admin.modules.common.entity.Organization;
import org.navistack.admin.modules.common.query.OrganizationQuery;
import org.navistack.admin.modules.mgmt.service.OrganizationService;
import org.navistack.admin.modules.mgmt.service.convert.OrganizationConverter;
import org.navistack.admin.modules.mgmt.service.dto.OrganizationDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationDao dao;

    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<OrganizationDto> paginate(OrganizationQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<Organization> entities = dao.selectWithPageable(query, pageable);
        Collection<OrganizationDto> dtos = OrganizationConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(OrganizationDto dto) {
        ensureUnique(dto);

        Organization entity = OrganizationConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(OrganizationDto dto) {
        ensureUnique(dto);

        Organization entity = OrganizationConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }

    protected void ensureUnique(OrganizationDto dto) {
        OrganizationQuery query = OrganizationQuery.builder()
                .code(dto.getCode())
                .build();
        Organization existedOne = dao.selectOne(query);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Organization already exists");
    }
}
