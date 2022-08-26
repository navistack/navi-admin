package org.navistack.admin.modules.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.RegionDao;
import org.navistack.admin.modules.common.entity.Region;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;
import org.navistack.framework.data.TreeUtils;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
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
}
