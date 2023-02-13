package org.navistack.admin.modules.mgmt.service.impl;

import org.navistack.admin.modules.common.dao.PrivilegeDao;
import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.common.query.PrivilegeQuery;
import org.navistack.admin.modules.mgmt.service.PrivilegeService;
import org.navistack.admin.modules.mgmt.service.convert.PrivilegeConverter;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
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
        long totalRecords = dao.count(query);
        List<Privilege> entities = dao.selectWithPageable(query, pageable);
        Collection<PrivilegeDto> dtos = PrivilegeConverter.INSTANCE.entitiesToDtos(entities);
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public void create(PrivilegeDto dto) {
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Privilege code has been taken already"));
        Asserts.state(dto.getParentId(), this::validateExistenceById, () -> new NoSuchEntityException("Parent does not exist"));

        Privilege entity = PrivilegeConverter.INSTANCE.dtoToEntity(dto);
        AuditingEntitySupport.insertAuditingProperties(entity);
        dao.insert(entity);
    }

    @Override
    public void modify(PrivilegeDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Privilege does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Privilege code has been taken already"));
        Asserts.state(dto.getParentId(), this::validateExistenceById, () -> new NoSuchEntityException("Parent does not exist"));

        Privilege entity = PrivilegeConverter.INSTANCE.dtoToEntity(dto);
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

    protected boolean validateAvailabilityOfCode(String code, Long modifiedId) {
        PrivilegeQuery query = PrivilegeQuery.builder()
                .code(code)
                .build();
        Privilege existingOne = dao.selectOne(query);

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
        PrivilegeQuery query = PrivilegeQuery.builder()
                .parentId(id)
                .build();
        return dao.count(query) <= 0;
    }

    // endregion
}
