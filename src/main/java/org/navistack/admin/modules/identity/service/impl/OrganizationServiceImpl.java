package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.OrganizationDao;
import org.navistack.admin.modules.identity.entity.Organization;
import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.admin.modules.identity.service.OrganizationService;
import org.navistack.admin.modules.identity.service.convert.OrganizationConverter;
import org.navistack.admin.modules.identity.service.convert.OrganizationDtoConverter;
import org.navistack.admin.modules.identity.service.dto.OrganizationDto;
import org.navistack.admin.support.mybatis.AuditingPropertiesSupport;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageBuilder;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.Asserts;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationDao dao;

    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<OrganizationDto> paginate(OrganizationQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        if (totalRecords <= 0) {
            return PageBuilder.emptyPage();
        }
        return dao.paginateByQuery(query, pageable)
                .stream()
                .map(OrganizationConverter.INSTANCE::toDto)
                .collect(PageBuilder.collector(pageable, totalRecords));
    }

    @Override
    public void create(OrganizationDto dto) {
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Organization code has been taken already"));
        Asserts.state(dto.getSuperId(), this::validateExistenceById, () -> new NoSuchEntityException("Superior does not exist"));

        Organization entity = OrganizationDtoConverter.INSTANCE.toEntity(dto);
        AuditingPropertiesSupport.created(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(OrganizationDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Organization does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Organization code has been taken already"));
        Asserts.state(dto.getSuperId(), this::validateExistenceById, () -> new NoSuchEntityException("Superior does not exist"));

        Organization entity = OrganizationDtoConverter.INSTANCE.toEntity(dto);
        AuditingPropertiesSupport.updated(entity);
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
        return id != null && dao.existsById(id);
    }

    protected boolean validateAvailabilityOfCode(String code, Long modifiedId) {
        Long currentId = dao.selectIdByCode(code);
        return currentId == null || currentId.equals(modifiedId);
    }

    protected boolean validateAbsenceOfSubordinate(Long superId) {
        return superId == null || !dao.existsBySuperId(superId);
    }

    // endregion
}
