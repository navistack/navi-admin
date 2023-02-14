package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.OrganizationDao;
import org.navistack.admin.modules.common.entity.Organization;
import org.navistack.admin.modules.common.query.OrganizationQuery;
import org.navistack.admin.modules.mgmt.service.OrganizationService;
import org.navistack.admin.modules.mgmt.service.convert.OrganizationConverter;
import org.navistack.admin.modules.mgmt.service.dto.OrganizationDto;
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
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Organization code has been taken already"));
        Asserts.state(dto.getSuperId(), this::validateExistenceById, () -> new NoSuchEntityException("Superior does not exist"));

        Organization entity = OrganizationConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(OrganizationDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Organization does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Organization code has been taken already"));
        Asserts.state(dto.getSuperId(), this::validateExistenceById, () -> new NoSuchEntityException("Superior does not exist"));

        Organization entity = OrganizationConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.updateAuditingProperties(entity);
        dao.updateById(entity);
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById, () -> new NoSuchEntityException("Organization does not exist"));
        Asserts.state(id, this::validateAbsenceOfSubordinate, () -> new ConstraintViolationException("Organization can not be removed as sub-organization(s) exist(s)"));
        dao.deleteById(id);
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.selectOneById(id) != null;
    }

    protected boolean validateAvailabilityOfCode(String code, Long modifiedId) {
        OrganizationQuery query = OrganizationQuery.builder()
                .code(code)
                .build();
        Organization existingOne = dao.selectOne(query);

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

    protected boolean validateAbsenceOfSubordinate(Long superId) {
        OrganizationQuery query = OrganizationQuery.builder()
                .superId(superId)
                .build();
        return dao.count(query) <= 0;
    }

    // endregion
}
