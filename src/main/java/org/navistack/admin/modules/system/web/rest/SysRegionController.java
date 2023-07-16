package org.navistack.admin.modules.system.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.system.web.rest.convert.RegionVmConverter;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;
import org.navistack.framework.data.TreeBuilder;
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
    public Collection<RegionVm> get() {
        List<Region> regions = regionDao.selectAll();
        Collection<RegionVm> vms = regions.stream()
                .map(RegionVmConverter.INSTANCE::fromEntity)
                .collect(TreeBuilder.collector());
        return vms;
    }

    @GetMapping("/{region}")
    @Operation(summary = "Get regions and their sub-regions recursively")
    public Collection<RegionVm> get(
            @PathVariable("region") String regionCode,
            @RequestParam(defaultValue = "true") boolean recursive
    ) {
        List<Region> regions;
        if (recursive) {
            RegionQuery regionQuery = RegionQuery.builder()
                    .code(regionCode)
                    .build();
            regions = regionDao.selectAllByQueryRecursively(regionQuery);
        } else {
            RegionQuery regionQuery = RegionQuery.builder()
                    .parentCode(regionCode)
                    .build();
            regions = regionDao.selectAllByQuery(regionQuery);
        }

        if (regions.isEmpty()) {
            return Collections.emptyList();
        }

        Collection<RegionVm> vms = regions.stream()
                .map(RegionVmConverter.INSTANCE::fromEntity)
                .collect(TreeBuilder.<RegionVm>of().orphanAsRoot(true).toCollector());
        return vms;
    }
}
