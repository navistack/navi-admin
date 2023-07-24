package org.navistack.admin.modules.system.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.system.web.rest.convert.RegionVmConverter;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;
import org.navistack.framework.data.TreeBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return regions.stream()
                .map(RegionVmConverter.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{region}")
    @Operation(summary = "Get regions and their sub-regions recursively")
    public Collection<RegionVm> get(
            @PathVariable("region") String regionCode,
            @RequestParam(defaultValue = "true") boolean recursive
    ) {
        List<Region> regions = recursive
                ? regionDao.selectAllHierarchicalByCode(regionCode)
                : regionDao.selectAllByParentCode(regionCode);
        if (regions.isEmpty()) {
            return Collections.emptyList();
        }

        return regions.stream()
                .map(RegionVmConverter.INSTANCE::fromEntity)
                .collect(TreeBuilder.<RegionVm>of().orphanAsRoot(true).toCollector());
    }
}
