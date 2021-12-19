package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.mgmt.service.RegionService;
import org.navistack.admin.modules.mgmt.service.dto.RegionDto;
import org.navistack.admin.modules.mgmt.service.dto.RegionQueryParams;
import org.navistack.framework.crudsupport.validation.groups.Create;
import org.navistack.framework.crudsupport.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/region")
@Tag(name = "Region Management")
public class RegionController {
    private final RegionService service;

    public RegionController(RegionService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:region:paginate')")
    @Operation(
            summary = "Query Paged list of regions",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Region Management")
    public RestResult<Page<RegionDto>, ?> paginate(RegionQueryParams params, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(params, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:region:create')")
    @Operation(
            summary = "Create a region",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Region Management")
    public RestResult<Void, ?> create(@Validated({Default.class, Create.class}) RegionDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:region:modify')")
    @Operation(
            summary = "Modify region",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Region Management")
    public RestResult<Void, ?> modify(@Validated({Default.class, Modify.class}) RegionDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:region:remove')")
    @Operation(
            summary = "Remove region",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Region Management")
    public RestResult<Void, ?> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
