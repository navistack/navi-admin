package org.navistack.admin.modules.common.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.common.service.RegionService;
import org.navistack.admin.modules.common.service.dto.RegionCreateDto;
import org.navistack.admin.modules.common.service.dto.RegionModifyDto;
import org.navistack.admin.modules.system.web.rest.vm.RegionVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/region")
@Tag(name = "Region Management")
@SecurityRequirement(name = "bearer-key")
public class RegionController {
    private final RegionService service;

    public RegionController(RegionService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:region:query')")
    @Operation(summary = "Query Paged list of regions")
    public Page<RegionVm> paginate(RegionQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:region:create')")
    @Operation(summary = "Create a region")
    public void create(@Validated RegionCreateDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:region:modify')")
    @Operation(summary = "Modify region")
    public void modify(@Validated RegionModifyDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:region:remove')")
    @Operation(summary = "Remove region")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}
