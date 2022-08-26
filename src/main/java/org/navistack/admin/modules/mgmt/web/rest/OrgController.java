package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.mgmt.service.OrgService;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;
import org.navistack.admin.modules.mgmt.service.dto.OrgQueryParams;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.mybatisplusplus.validation.groups.Create;
import org.navistack.framework.mybatisplusplus.validation.groups.Modify;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/org")
@Tag(name = "Organization Management")
public class OrgController {
    private final OrgService service;

    public OrgController(OrgService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:organization:paginate')")
    @Operation(
            summary = "Query Paged list of organizations",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Organization Management")
    public RestResult.Ok<Page<OrgDto>> paginate(OrgQueryParams queryParams, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(queryParams, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:organization:create')")
    @Operation(
            summary = "Create an organization",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Organization Management")
    public RestResult.Ok<Void> create(@Validated({Default.class, Create.class}) OrgDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:organization:modify')")
    @Operation(
            summary = "Modify organization",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Organization Management")
    public RestResult.Ok<Void> modify(@Validated({Default.class, Modify.class}) OrgDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:organization:remove')")
    @Operation(
            summary = "Remove organization",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Organization Management")
    public RestResult.Ok<Void> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
