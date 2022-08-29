package org.navistack.admin.modules.system.web.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;
import org.navistack.framework.data.TreeUtils;
import org.navistack.framework.utils.Strings;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sys/regions")
@Tag(name = "System")
public class SysRegionController {
    private final RegionDao regionDao;

    public SysRegionController(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @GetMapping
    @Operation(summary = "Get regions and their sub-regions recursively")
    @Tag(name = "System")
    public RestResult.Ok<Collection<RegionVm>> get() {
        List<Region> regions = regionDao.selectList(Wrappers.emptyWrapper());
        Collection<RegionVm> vms = TreeUtils.treeize(regions, RegionVm::of);
        return RestResult.ok(vms);
    }

    @GetMapping("/{region}")
    @Operation(summary = "Get regions and their sub-regions recursively")
    @Tag(name = "System")
    public RestResult.Ok<Collection<RegionVm>> get(
            @PathVariable("region") String regionCode,
            @RequestParam(defaultValue = "true") boolean recursive
    ) {
        List<Region> regions = null;
        if (recursive) {
            Wrapper<Region> wrapper = Wrappers.<Region>lambdaQuery()
                    .eq(Strings.hasText(regionCode), Region::getCode, regionCode);
            regions = regionDao.selectListRecursively(wrapper);
        } else {
            Wrapper<Region> wrapper = Wrappers.<Region>lambdaQuery()
                    .eq(Strings.hasText(regionCode), Region::getParentCode, regionCode);
            regions = regionDao.selectList(wrapper);
        }

        if (regions.isEmpty()) {
            return RestResult.ok(Collections.emptyList());
        }

        Collection<RegionVm> vms = TreeUtils.treeize(regions, regionCode, RegionVm::of);
        return RestResult.ok(vms);
    }
}
