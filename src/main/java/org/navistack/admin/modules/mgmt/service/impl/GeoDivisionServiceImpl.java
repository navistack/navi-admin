package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.entity.GeoDivision;
import org.navistack.admin.modules.mgmt.service.GeoDivisionService;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionDto;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionQueryParams;
import org.navistack.admin.support.problems.EntityDuplicatedProblem;
import org.navistack.admin.utils.MyBatisPlusUtils;
import org.navistack.framework.core.utils.StaticModelMapper;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeoDivisionServiceImpl implements GeoDivisionService {
    private final org.navistack.admin.modules.common.dao.GeoDivisionDao dao;

    public GeoDivisionServiceImpl(org.navistack.admin.modules.common.dao.GeoDivisionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<GeoDivision> list() {
        return dao.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public Page<GeoDivision> paginate(GeoDivisionQueryParams queryParams, Pageable pageable) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();
        String parentCode = queryParams.getParentCode();

        Wrapper<GeoDivision> wrapper = Wrappers.<GeoDivision>lambdaQuery()
                .eq(id != null, GeoDivision::getId, id)
                .eq(code != null, GeoDivision::getCode, code)
                .like(name != null, GeoDivision::getName, name)
                .eq(parentCode != null, GeoDivision::getParentCode, parentCode);

        return MyBatisPlusUtils.PageUtils.toPage(
                dao.selectPage(
                        MyBatisPlusUtils.PageUtils.fromPageable(pageable),
                        wrapper
                )
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(GeoDivisionDto dto) {
        dto.setId(null);

        Long cnt = dao.selectCount(
                Wrappers.<GeoDivision>lambdaQuery()
                        .eq(GeoDivision::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Item existed");
        }

        GeoDivision division = StaticModelMapper.map(dto, GeoDivision.class);

        dao.insert(division);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(GeoDivisionDto dto) {
        Long cnt = dao.selectCount(
                Wrappers.<GeoDivision>lambdaQuery()
                        .eq(GeoDivision::getCode, dto.getCode())
                        .ne(GeoDivision::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Item existed");
        }

        GeoDivision division = StaticModelMapper.map(dto, GeoDivision.class);

        dao.updateById(division);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }
}
