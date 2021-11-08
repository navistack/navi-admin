package org.navistack.admin.modules.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.dao.GeoDivisionDao;
import org.navistack.admin.modules.common.entity.GeoDivision;
import org.navistack.admin.modules.system.web.rest.vm.GeoDivisionVm;
import org.navistack.framework.data.TreeUtils;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/sys/geo-division")
@Tag(name = "System")
public class SysGeoDivisionController {
    private final GeoDivisionDao geoDivisionDao;

    public SysGeoDivisionController(GeoDivisionDao geoDivisionDao) {
        this.geoDivisionDao = geoDivisionDao;
    }

    @GetMapping
    @Operation(summary = "Get geographical divisions and their subdivisions recursively")
    @Tag(name = "System")
    public RestResult<Collection<GeoDivisionVm>, ?> get() {
        List<GeoDivision> divisions = geoDivisionDao.selectList(Wrappers.emptyWrapper());
        Collection<GeoDivisionVm> vms = TreeUtils.treeize(divisions, GeoDivisionVm::of);
        return RestResult.ok(vms);
    }
}
