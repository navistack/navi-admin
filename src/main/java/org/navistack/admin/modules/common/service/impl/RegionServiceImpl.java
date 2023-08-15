package org.navistack.admin.modules.common.service.impl;

import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.dtobj.RegionDo;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.common.service.RegionService;
import org.navistack.admin.modules.common.service.convert.RegionDoConvert;
import org.navistack.admin.modules.common.service.convert.RegionVmConvert;
import org.navistack.admin.modules.common.service.dto.RegionCreateDto;
import org.navistack.admin.modules.common.service.dto.RegionModifyDto;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;
import org.navistack.admin.support.mybatis.AuditingPropertiesSupport;
import org.navistack.framework.core.error.ConstraintViolationException;
import org.navistack.framework.core.error.DomainValidationException;
import org.navistack.framework.core.error.NoSuchEntityException;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageBuilder;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.utils.Asserts;
import org.navistack.framework.utils.Strings;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionDao dao;

    public RegionServiceImpl(RegionDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<RegionVm> paginate(RegionQuery query, Pageable pageable) {
        long totalRecords = dao.countByQuery(query);
        if (totalRecords <= 0) {
            return PageBuilder.emptyPage();
        }
        return dao.paginateByQuery(query, pageable)
                .stream()
                .map(RegionVmConvert.INSTANCE::from)
                .collect(PageBuilder.collector(pageable, totalRecords));
    }

    @Override
    public void create(RegionCreateDto dto) {
        Asserts.state(dto.getCode(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Region code has been taken already"));
        Asserts.state(dto.getParentCode(), this::validateExistenceByCode, () -> new NoSuchEntityException("Parent does not exist"));

        RegionDo dtObj = RegionDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.created(dtObj);
        dao.insert(dtObj);
    }

    @Override
    public void modify(RegionModifyDto dto) {
        Asserts.state(dto.getId(), this::validateExistenceById, () -> new NoSuchEntityException("Region does not exist"));
        Asserts.state(dto.getCode(), dto.getId(), this::validateAvailabilityOfCode, () -> new DomainValidationException("Region code has been taken already"));
        Asserts.state(dto.getParentCode(), this::validateExistenceByCode, () -> new NoSuchEntityException("Parent does not exist"));

        RegionDo dtObj = RegionDoConvert.INSTANCE.from(dto);
        AuditingPropertiesSupport.updated(dtObj);
        dao.updateById(dtObj);
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

    protected boolean validateExistenceByCode(String code) {
        return code != null && dao.existsByCode(code);
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
        String regionCode = dao.selectCodeById(id);
        return !Strings.hasText(regionCode) || !dao.existsByParentCode(regionCode);
    }

    // endregion
}
