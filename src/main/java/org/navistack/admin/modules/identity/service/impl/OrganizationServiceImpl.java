package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.OrganizationDao;
import org.navistack.admin.modules.identity.dtobj.OrganizationDo;
import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.admin.modules.identity.service.OrganizationService;
import org.navistack.admin.modules.identity.service.convert.OrganizationDoConvert;
import org.navistack.admin.modules.identity.service.convert.OrganizationVmConvert;
import org.navistack.admin.modules.identity.service.dto.OrganizationCreateDto;
import org.navistack.admin.modules.identity.service.dto.OrganizationModifyDto;
import org.navistack.admin.modules.identity.service.vm.OrganizationVm;
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
    public Page<OrganizationVm> paginate(OrganizationQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        if (totalRecords <= 0) {
            return PageBuilder.emptyPage();
        }
        return dao.paginateByQuery(query, pageable)
                .stream()
                .map(OrganizationVmConvert.INSTANCE::from)
                .collect(PageBuilder.collector(pageable, totalRecords));
    }

    @Override
    public void create(OrganizationCreateDto dto) {
        Asserts.state(dto.getCode(), this::validateAvailabilityOfCode,
                () -> new DomainValidationException("Organization code has been taken already"));
        Asserts.state(dto.getSuperId(), this::validateExistenceById,
                () -> new NoSuchEntityException("Superior does not exist"));

        OrganizationDo dtObj = OrganizationDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.created(dtObj);
        dao.insert(dtObj);
    }

    @Override
    public void modify(OrganizationModifyDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById,
                () -> new NoSuchEntityException("Organization does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode,
                () -> new DomainValidationException("Organization code has been taken already"));
        Asserts.state(dto.getSuperId(), this::validateExistenceById,
                () -> new NoSuchEntityException("Superior does not exist"));

        OrganizationDo dtObj = OrganizationDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.updated(dtObj);
        dao.updateById(dtObj);
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById,
                () -> new NoSuchEntityException("Organization does not exist"));
        Asserts.state(id, this::validateAbsenceOfSubordinate,
                () -> new ConstraintViolationException("Organization cant be removed as sub-organization(s) exist(s)"));
        dao.deleteById(id);
    }

    // region Validation methods

    protected boolean validateExistenceById(Long id) {
        return id != null && dao.existsById(id);
    }

    protected boolean validateAvailabilityOfCode(String code) {
        Long currentId = dao.selectIdByCode(code);
        return currentId == null;
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
