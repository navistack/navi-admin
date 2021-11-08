package org.navistack.admin.modules.mgmt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.navistack.admin.modules.common.dao.DictItemDao;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.mgmt.service.DictItemService;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.admin.modules.mgmt.service.dto.DictItemQueryParams;
import org.navistack.admin.support.problems.EntityDuplicatedProblem;
import org.navistack.admin.utils.MyBatisPlusUtils;
import org.navistack.framework.core.utils.StaticModelMapper;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DictItemServiceImpl implements DictItemService {
    private final DictItemDao dao;

    public DictItemServiceImpl(DictItemDao dao) {
        this.dao = dao;
    }

    @Override
    public List<DictItem> list() {
        return dao.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public Page<DictItem> paginate(DictItemQueryParams queryParams, Pageable pageable) {
        Long id = queryParams.getId();
        String name = queryParams.getName();
        String itKey = queryParams.getItKey();
        String dictCode = queryParams.getDictCode();

        Wrapper<DictItem> wrapper = Wrappers.<DictItem>lambdaQuery()
                .eq(id != null, DictItem::getId, id)
                .like(name != null, DictItem::getName, name)
                .eq(itKey != null, DictItem::getItKey, itKey)
                .eq(dictCode != null, DictItem::getDictCode, dictCode);

        return MyBatisPlusUtils.PageUtils.toPage(
                dao.selectPage(
                        MyBatisPlusUtils.PageUtils.fromPageable(pageable),
                        wrapper
                )
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DictItemDto dto) {
        dto.setId(null);

        Long cnt = dao.selectCount(
                Wrappers.<DictItem>lambdaQuery()
                        .eq(DictItem::getDictCode, dto.getDictCode())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Item existed");
        }

        DictItem dictItem = StaticModelMapper.map(dto, DictItem.class);

        dao.insert(dictItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(DictItemDto dto) {
        Long cnt = dao.selectCount(
                Wrappers.<DictItem>lambdaQuery()
                        .eq(DictItem::getDictCode, dto.getDictCode())
                        .ne(DictItem::getId, dto.getId())
        );

        if (cnt > 0) {
            throw new EntityDuplicatedProblem("Item existed");
        }

        DictItem dictItem = StaticModelMapper.map(dto, DictItem.class);

        dao.updateById(dictItem);
    }

    @Override
    public void remove(Long id) {
        dao.deleteById(id);
    }
}
