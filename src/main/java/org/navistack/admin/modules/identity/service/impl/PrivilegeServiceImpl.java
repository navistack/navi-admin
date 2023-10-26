package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.dtobj.PrivilegeDo;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.admin.modules.identity.service.PrivilegeService;
import org.navistack.admin.modules.identity.service.convert.PrivilegeDoConvert;
import org.navistack.admin.modules.identity.service.convert.PrivilegeVmConvert;
import org.navistack.admin.modules.identity.service.dto.PrivilegeCreateDto;
import org.navistack.admin.modules.identity.service.dto.PrivilegeModifyDto;
import org.navistack.admin.modules.identity.service.vm.PrivilegeVm;
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
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeDao dao;

    public PrivilegeServiceImpl(PrivilegeDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<PrivilegeVm> paginate(PrivilegeQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        if (totalRecords <= 0) {
            return PageBuilder.emptyPage();
        }
        return dao.paginateByQuery(query, pageable)
                .stream()
                .map(PrivilegeVmConvert.INSTANCE::from)
                .collect(PageBuilder.collector(pageable, totalRecords));
    }

    @Override
    public void create(PrivilegeCreateDto dto) {
        Asserts.state(dto.getCode(), this::validateAvailabilityOfCode,
                () -> new DomainValidationException("Privilege code has been taken already"));
        Asserts.state(dto.getParentId(), this::validateExistenceById,
                () -> new NoSuchEntityException("Parent does not exist"));

        PrivilegeDo dtObj = PrivilegeDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.created(dtObj);
        dao.insert(dtObj);
    }

    @Override
    public void modify(PrivilegeModifyDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById,
                () -> new NoSuchEntityException("Privilege does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode,
                () -> new DomainValidationException("Privilege code has been taken already"));
        Asserts.state(dto.getParentId(), this::validateExistenceById,
                () -> new NoSuchEntityException("Parent does not exist"));

        PrivilegeDo dtObj = PrivilegeDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.updated(dtObj);
        dao.updateById(dtObj);
    }

    @Override
    public void remove(Long id) {
        Asserts.state(id, this::validateExistenceById,
                () -> new NoSuchEntityException("Privilege does not exist"));
        Asserts.state(id, this::validateAbsenceOfSubordinate,
                () -> new ConstraintViolationException("Privileges can not be removed as sub-privilege(s) exist(s)"));
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

    protected boolean validateAbsenceOfSubordinate(Long id) {
        return id == null || !dao.existsByParentId(id);
    }

    // endregion
}
