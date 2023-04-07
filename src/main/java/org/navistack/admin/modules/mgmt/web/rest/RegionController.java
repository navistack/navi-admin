package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.navistack.admin.modules.common.query.RegionQuery;
import org.navistack.admin.modules.mgmt.service.RegionService;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Page<RegionDto> paginate(RegionQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:region:create')")
    @Operation(summary = "Create a region")
    public void create(@Validated({Default.class, Create.class}) RegionDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:region:modify')")
    @Operation(summary = "Modify region")
    public void modify(@Validated({Default.class, Modify.class}) RegionDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:region:remove')")
    @Operation(summary = "Remove region")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}
