package org.navistack.admin.modules.system.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.DictDao;
import org.navistack.admin.modules.common.dao.DictItemDao;
import org.navistack.admin.modules.common.entity.Dict;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.common.query.DictItemQuery;
import org.navistack.admin.modules.common.query.DictQuery;
import org.navistack.admin.modules.system.web.rest.vm.DictItemVm;
import org.navistack.admin.modules.system.web.rest.vm.DictVm;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys/dict")
@Tag(name = "System")
public class SysDictController {
    private final DictDao dictDao;
    private final DictItemDao dictItemDao;

    public SysDictController(DictDao dictDao, DictItemDao dictItemDao) {
        this.dictDao = dictDao;
        this.dictItemDao = dictItemDao;
    }

    @GetMapping
    @Operation(summary = "Get directories and their items")
    public RestResult.Ok<Collection<DictVm>> get() {
        List<Dict> dicts = dictDao.selectAll();
        if (dicts.isEmpty()) {
            return RestResult.ok(Collections.emptyList());
        }

        Collection<DictVm> dictVms = dicts.stream()
                .map(d -> DictVm.of(d.getCode(), d.getName()))
                .collect(Collectors.toList());

        List<DictItem> items = dictItemDao.selectAll();
        if (items.isEmpty()) {
            return RestResult.ok(dictVms);
        }

        Map<String, DictVm> dictVmMap = dictVms.stream()
                .collect(
                        Collectors.toMap(
                                DictVm::getCode,
                                Function.identity()
                        )
                );

        for (DictItem item : items) {
            DictItemVm itemVm = DictItemVm.of(item.getName(), item.getItKey(), item.getItValue());

            DictVm dictVm = dictVmMap.get(item.getDictCode());

            if (dictVm != null) {
                dictVm.getItems().add(itemVm);
            }
        }

        return RestResult.ok(dictVms);
    }

    @GetMapping("/{dict:[A-Za-z0-9$_]{1,48}}")
    @Operation(summary = "Get directory and its items")
    public RestResult.Ok<DictVm> get(@PathVariable("dict") String dictCode) {
        DictQuery dictQuery = DictQuery.builder()
                .code(dictCode)
                .build();

        Dict dict = dictDao.selectOne(dictQuery);
        if (dict == null) {
            return RestResult.ok(null);
        }

        DictVm dictVm = DictVm.of(dict.getCode(), dict.getName());

        DictItemQuery itemQuery = DictItemQuery.builder()
                .dictCode(dictCode)
                .build();
        List<DictItem> items = dictItemDao.select(itemQuery);
        if (items.isEmpty()) {
            return RestResult.ok(dictVm);
        }

        Collection<DictItemVm> dictItemVms = items.stream()
                .map(item -> DictItemVm.of(item.getName(), item.getItKey(), item.getItValue()))
                .collect(Collectors.toList());
        dictVm.setItems(dictItemVms);

        return RestResult.ok(dictVm);
    }

    @GetMapping("/{dict:[A-Za-z0-9$_]{1,48}}/items")
    @Operation(summary = "Get items of directory")
    public RestResult.Ok<Collection<DictItemVm>> getItem(@PathVariable("dict") String dictCode) {
        DictItemQuery dictItemQuery = DictItemQuery.builder()
                .dictCode(dictCode)
                .build();
        List<DictItem> items = dictItemDao.select(dictItemQuery);
        if (items.isEmpty()) {
            return RestResult.ok(Collections.emptyList());
        }

        Collection<DictItemVm> dictItemVms = items.stream()
                .map(item -> DictItemVm.of(item.getName(), item.getItKey(), item.getItValue()))
                .collect(Collectors.toList());

        return RestResult.ok(dictItemVms);
    }
}
