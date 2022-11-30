package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.mgmt.service.RegionService;
import org.navistack.admin.modules.mgmt.service.convert.RegionConverter;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.EntityDuplicationException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionDao dao;

    public RegionServiceImpl(RegionDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<RegionDto> paginate(RegionQuery query, Pageable pageable) {
        long totalRecords = dao.count(query);
        List<Region> entities = dao.selectWithPageable(query, pageable);
        Collection<RegionDto> dtos = RegionConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(RegionDto dto) {
        ensureUnique(dto);

        Region entity = RegionConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(RegionDto dto) {
        ensureUnique(dto);

        Region entity = RegionConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }

    protected void ensureUnique(RegionDto dto) {
        RegionQuery queryDto = RegionQuery.builder()
                .code(dto.getCode())
                .build();
        Region existedOne = dao.selectOne(queryDto);

        if (existedOne == null) {
            return;
        }

        if (!existedOne.getId().equals(dto.getId())) {
            return;
        }

        throw new EntityDuplicationException("Region already exists");
    }
}
