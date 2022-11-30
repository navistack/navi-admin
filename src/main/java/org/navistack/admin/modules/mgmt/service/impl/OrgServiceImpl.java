package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.OrgDao;
import org.navistack.admin.modules.common.entity.Org;
import org.navistack.admin.modules.common.query.OrgQuery;
import org.navistack.admin.modules.mgmt.service.OrgService;
import org.navistack.admin.modules.mgmt.service.convert.OrgConverter;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {
    private final OrgDao dao;

    public OrgServiceImpl(OrgDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<OrgDto> paginate(OrgQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<Org> entities = dao.selectWithPageable(query, pageable);
        Collection<OrgDto> dtos = OrgConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(OrgDto dto) {
        ensureUnique(dto);

        Org entity = OrgConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(OrgDto dto) {
        ensureUnique(dto);

        Org entity = OrgConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }

    protected void ensureUnique(OrgDto dto) {
        OrgQuery query = OrgQuery.builder()
                .code(dto.getCode())
                .build();
        Org existedOne = dao.selectOne(query);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Organization has existed");
    }
}
