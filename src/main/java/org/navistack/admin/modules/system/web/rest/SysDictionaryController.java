package org.navistack.admin.modules.system.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.DictionaryDao;
import org.navistack.admin.modules.common.dao.DictionaryItemDao;
import org.navistack.admin.modules.common.entity.Dictionary;
import org.navistack.admin.modules.common.entity.DictionaryItem;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.system.web.rest.convert.DictionaryItemVmConverter;
import org.navistack.admin.modules.system.web.rest.convert.DictionaryVmConverter;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryItemVm;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryVm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys/dictionary")
@Tag(name = "System")
public class SysDictionaryController {
    private final DictionaryDao dictionaryDao;
    private final DictionaryItemDao dictionaryItemDao;

    public SysDictionaryController(DictionaryDao dictionaryDao, DictionaryItemDao dictionaryItemDao) {
        this.dictionaryDao = dictionaryDao;
        this.dictionaryItemDao = dictionaryItemDao;
    }

    @GetMapping
    @Operation(summary = "Get directories and their items")
    public Collection<DictionaryVm> get() {
        List<Dictionary> dictionaries = dictionaryDao.selectAll();
        if (dictionaries.isEmpty()) {
            return Collections.emptyList();
        }

        List<DictionaryItem> items = dictionaryItemDao.selectAll();
        if (items.isEmpty()) {
            Collection<DictionaryVm> dictionaryVms = dictionaries.stream()
                    .map(DictionaryVmConverter.INSTANCE::fromEntity)
                    .collect(Collectors.toList());
            return dictionaryVms;
        }

        Collection<DictionaryVm> dictionaryVms = new ArrayList<>(dictionaries.size());
        Map<Long, DictionaryVm> dictVmMap = new HashMap<>(dictionaries.size());
        for (Dictionary dict : dictionaries) {
            DictionaryVm vm = DictionaryVmConverter.INSTANCE.fromEntity(dict);
            dictVmMap.put(dict.getId(), vm);
        }

        for (DictionaryItem item : items) {
            DictionaryItemVm itemVm = DictionaryItemVmConverter.INSTANCE.fromEntity(item);

            DictionaryVm dictionaryVm = dictVmMap.get(item.getDictionaryId());

            if (dictionaryVm != null) {
                dictionaryVm.getItems().add(itemVm);
            }
        }

        return dictionaryVms;
    }

    @GetMapping("/{dictionary:[A-Za-z0-9$_]{1,48}}")
    @Operation(summary = "Get directory and its items")
    public DictionaryVm get(@PathVariable("dictionary") String dictionaryCode) {
        DictionaryQuery dictionaryQuery = DictionaryQuery.builder()
                .code(dictionaryCode)
                .build();

        Dictionary dictionary = dictionaryDao.selectByQuery(dictionaryQuery);
        if (dictionary == null) {
            return null;
        }

        DictionaryVm dictionaryVm = DictionaryVmConverter.INSTANCE.fromEntity(dictionary);

        DictionaryItemQuery itemQuery = DictionaryItemQuery.builder()
                .dictionaryId(dictionary.getId())
                .build();
        List<DictionaryItem> items = dictionaryItemDao.selectAllByQuery(itemQuery);
        if (items.isEmpty()) {
            return dictionaryVm;
        }

        Collection<DictionaryItemVm> dictionaryItemVms = items.stream()
                .map(DictionaryItemVmConverter.INSTANCE::fromEntity)
                .collect(Collectors.toList());
        dictionaryVm.setItems(dictionaryItemVms);

        return dictionaryVm;
    }

    @GetMapping("/{dictionary:[A-Za-z0-9$_]{1,48}}/items")
    @Operation(summary = "Get items of directory")
    public Collection<DictionaryItemVm> getItem(@PathVariable("dictionary") String dictionaryCode) {
        DictionaryQuery dictionaryQuery = DictionaryQuery.builder()
                .code(dictionaryCode)
                .build();
        Dictionary dictionary = dictionaryDao.selectByQuery(dictionaryQuery);
        if (dictionary == null) {
            return Collections.emptyList();
        }

        DictionaryItemQuery dictionaryItemQuery = DictionaryItemQuery.builder()
                .dictionaryId(dictionary.getId())
                .build();
        List<DictionaryItem> items = dictionaryItemDao.selectAllByQuery(dictionaryItemQuery);
        if (items.isEmpty()) {
            return Collections.emptyList();
        }

        Collection<DictionaryItemVm> dictionaryItemVms = items.stream()
                .map(DictionaryItemVmConverter.INSTANCE::fromEntity)
                .collect(Collectors.toList());

        return dictionaryItemVms;
    }
}
