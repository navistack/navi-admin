package org.navistack.admin.modules.system.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.DictionaryDao;
import org.navistack.admin.modules.common.dao.DictionaryItemDao;
import org.navistack.admin.modules.common.dtobj.DictionaryDo;
import org.navistack.admin.modules.common.dtobj.DictionaryItemDo;
import org.navistack.admin.modules.system.web.rest.convert.DictionaryItemVmConvert;
import org.navistack.admin.modules.system.web.rest.convert.DictionaryVmConvert;
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
        List<DictionaryDo> dictionaries = dictionaryDao.selectAll();
        if (dictionaries.isEmpty()) {
            return Collections.emptyList();
        }

        List<DictionaryItemDo> items = dictionaryItemDao.selectAll();
        if (items.isEmpty()) {
            return dictionaries.stream()
                    .map(DictionaryVmConvert.INSTANCE::from)
                    .collect(Collectors.toList());
        }

        Collection<DictionaryVm> dictionaryVms = new ArrayList<>(dictionaries.size());
        Map<Long, DictionaryVm> dictVmMap = new HashMap<>(dictionaries.size());
        for (DictionaryDo dict : dictionaries) {
            DictionaryVm vm = DictionaryVmConvert.INSTANCE.from(dict);
            dictVmMap.put(dict.getId(), vm);
        }

        for (DictionaryItemDo item : items) {
            DictionaryVm dictionaryVm = dictVmMap.get(item.getDictionaryId());
            if (dictionaryVm == null) {
                continue;
            }
            Collection<DictionaryItemVm> vmItems = dictionaryVm.getItems();
            if (vmItems == null) {
                vmItems = new ArrayList<>();
                dictionaryVm.setItems(vmItems);
            }
            DictionaryItemVm itemVm = DictionaryItemVmConvert.INSTANCE.from(item);
            vmItems.add(itemVm);
        }

        return dictionaryVms;
    }

    @GetMapping("/{dictionary:[A-Za-z0-9$_]{1,48}}")
    @Operation(summary = "Get directory and its items")
    public DictionaryVm get(@PathVariable("dictionary") String dictionaryCode) {
        DictionaryDo dictionary = dictionaryDao.selectByCode(dictionaryCode);
        if (dictionary == null) {
            return null;
        }

        DictionaryVm dictionaryVm = DictionaryVmConvert.INSTANCE.from(dictionary);
        List<DictionaryItemDo> items = dictionaryItemDao.selectAllByDictionaryId(dictionary.getId());
        if (items.isEmpty()) {
            return dictionaryVm;
        }

        Collection<DictionaryItemVm> dictionaryItemVms = items.stream()
                .map(DictionaryItemVmConvert.INSTANCE::from)
                .collect(Collectors.toList());
        dictionaryVm.setItems(dictionaryItemVms);
        return dictionaryVm;
    }

    @GetMapping("/{dictionary:[A-Za-z0-9$_]{1,48}}/items")
    @Operation(summary = "Get items of directory")
    public Collection<DictionaryItemVm> getItem(@PathVariable("dictionary") String dictionaryCode) {
        Long dictionaryId = dictionaryDao.selectIdByCode(dictionaryCode);
        if (dictionaryId == null) {
            return Collections.emptyList();
        }

        List<DictionaryItemDo> items = dictionaryItemDao.selectAllByDictionaryId(dictionaryId);
        if (items.isEmpty()) {
            return Collections.emptyList();
        }

        return items.stream()
                .map(DictionaryItemVmConvert.INSTANCE::from)
                .collect(Collectors.toList());
    }
}
