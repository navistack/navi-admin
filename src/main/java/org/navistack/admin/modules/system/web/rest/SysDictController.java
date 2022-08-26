package org.navistack.admin.modules.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.DictDao;
import org.navistack.admin.modules.common.dao.DictItemDao;
import org.navistack.admin.modules.common.entity.DictItem;
import org.navistack.admin.modules.system.web.rest.vm.DictItemVm;
import org.navistack.admin.modules.system.web.rest.vm.DictVm;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
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
    @Tag(name = "System")
    public RestResult.Ok<Collection<DictVm>> get() {
        Collection<DictVm> dictVms = dictDao.selectList(Wrappers.emptyWrapper())
                .stream()
                .map(dict -> DictVm.of(dict.getCode(), dict.getName()))
                .collect(Collectors.toList());

        Map<String, DictVm> dictVmMap = dictVms.stream()
                .collect(
                        Collectors.toMap(
                                DictVm::getCode,
                                Function.identity()
                        )
                );

        for (DictItem item : dictItemDao.selectList(Wrappers.emptyWrapper())) {
            DictItemVm itemVm = DictItemVm.of(item.getName(), item.getItKey(), item.getItValue());

            DictVm dictVm = dictVmMap.get(item.getDictCode());

            if (dictVm != null) {
                dictVm.getItems().add(itemVm);
            }
        }

        return RestResult.ok(dictVms);
    }
}
