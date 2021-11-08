package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.DictDao;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.mgmt.service.DictService;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.admin.modules.mgmt.service.dto.DictQueryParams;
import org.navistack.admin.support.problems.EntityDuplicatedProblem;
import org.navistack.admin.utils.MyBatisPlusUtils;
import org.navistack.framework.core.utils.StaticModelMapper;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {
    private final DictDao dao;

    public DictServiceImpl(DictDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Dict> list() {
        return dao.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public Page<Dict> paginate(DictQueryParams queryParams, Pageable pageable) {
        Long id = queryParams.getId();
        String code = queryParams.getCode();
        String name = queryParams.getName();

        Wrapper<Dict> wrapper = Wrappers.<Dict>lambdaQuery()
                .eq(id != null, Dict::getId, id)
                .eq(code != null, Dict::getCode, code)
                .like(name != null, Dict::getName, name);


        return MyBatisPlusUtils.PageUtils.toPage(
                dao.selectPage(
                        MyBatisPlusUtils.PageUtils.fromPageable(pageable),
                        wrapper
                )
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DictDto dto) {
        dto.setId(null);

        Long cnt = dao.selectCount(
                Wrappers.<Dict>lambdaQuery()
                        .eq(Dict::getCode, dto.getCode())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Dict existed");
        }

        Dict dict = StaticModelMapper.map(dto, Dict.class);

        dao.insert(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(DictDto dto) {
        Long cnt = dao.selectCount(
                Wrappers.<Dict>lambdaQuery()
                        .eq(Dict::getCode, dto.getCode())
                        .ne(Dict::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Dict existed");
        }

        Dict dict = StaticModelMapper.map(dto, Dict.class);

        dao.updateById(dict);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }
}
