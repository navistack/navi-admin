package org.navistack.admin.modules.identity.service.impl;

import org.navistack.admin.modules.identity.dao.PrivilegeDao;
import org.navistack.admin.modules.identity.entity.Privilege;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.admin.modules.identity.service.PrivilegeService;
import org.navistack.admin.modules.identity.service.convert.PrivilegeConverter;
import org.navistack.admin.modules.identity.service.convert.PrivilegeDtoConverter;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;
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
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeDao dao;

    public PrivilegeServiceImpl(PrivilegeDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<PrivilegeDto> paginate(PrivilegeQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        List<Privilege> entities = dao.paginateByQuery(query, pageable);
        Collection<PrivilegeDto> dtos = PrivilegeConverter.INSTANCE.toDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(PrivilegeDto dto) {
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Privilege code has been taken already"));
        Asserts.state(dto.getParentId(), this::validateExistenceById, () -> new NoSuchEntityException("Parent does not exist"));

        Privilege entity = PrivilegeDtoConverter.INSTANCE.toEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(PrivilegeDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Privilege does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Privilege code has been taken already"));
        Asserts.state(dto.getParentId(), this::validateExistenceById, () -> new NoSuchEntityException("Parent does not exist"));

        Privilege entity = PrivilegeDtoConverter.INSTANCE.toEntity(dto);
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
        return id != null && dao.existsById(id);
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
