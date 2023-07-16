package org.navistack.admin.modules.common.service.impl;

import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.common.service.RegionService;
import org.navistack.admin.modules.common.service.convert.RegionConverter;
import org.navistack.admin.modules.common.service.convert.RegionDtoConverter;
import org.navistack.admin.modules.common.service.dto.RegionDto;
import org.navistack.admin.support.mybatis.AuditingEntitySupport;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.Asserts;
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
        Collection<RegionDto> dtos = RegionConverter.INSTANCE.toDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(RegionDto dto) {
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Region code has been taken already"));
        Asserts.state(dto.getParentCode(), this::validateExistenceByCode, () -> new NoSuchEntityException("Parent does not exist"));

        Region entity = RegionDtoConverter.INSTANCE.toEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(RegionDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Region does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Region code has been taken already"));
        Asserts.state(dto.getParentCode(), this::validateExistenceByCode, () -> new NoSuchEntityException("Parent does not exist"));

        Region entity = RegionDtoConverter.INSTANCE.toEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById, () -> new NoSuchEntityException("Privilege does not exist"));
        Asserts.state(id, this::validateAbsenceOfSubordinate, () -> new ConstraintViolationException("Privileges can not be removed as sub-privilege(s) exist(s)"));

        dao.deleteById(id);
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.selectOneById(id) != null;
    }

    protected boolean validateExistenceByCode(String code) {
        if (code == null) {
            return false;
        }

        RegionQuery query = RegionQuery.builder()
                .code(code)
                .build();

        return dao.selectOne(query) != null;
    }

    protected boolean validateAvailabilityOfCode(String code, Long modifiedId) {
        RegionQuery query = RegionQuery.builder()
                .code(code)
                .build();
        Region existingOne = dao.selectOne(query);

        if (existingOne == null) {
            return true;
        }

        if (!existingOne.getCode().equals(code)) {
            return true;
        }

        if (existingOne.getId().equals(modifiedId)) {
            return true;
        }

        return false;
    }

    protected boolean validateAbsenceOfSubordinate(Long id) {
        Region region = dao.selectOneById(id);

        if (region == null) {
            return true;
        }

        String regionCode = region.getCode();

        if (regionCode == null || regionCode.isEmpty()) {
            return true;
        }

        RegionQuery query = RegionQuery.builder()
                .parentCode(regionCode)
                .build();
        return dao.count(query) <= 0;
    }

    // endregion
}
