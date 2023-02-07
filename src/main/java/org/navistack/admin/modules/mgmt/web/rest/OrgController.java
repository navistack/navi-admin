package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.navistack.admin.modules.common.query.OrgQuery;
import org.navistack.admin.modules.mgmt.service.OrgService;
import org.navistack.admin.modules.mgmt.service.dto.OrgDto;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/org")
@Tag(name = "Organization Management")
@SecurityRequirement(name = "bearer-key")
public class OrgController {
    private final OrgService service;

    public OrgController(OrgService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:organization:query')")
    @Operation(summary = "Query Paged list of organizations")
    public RestResult.Ok<Page<OrgDto>> paginate(OrgQuery query, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(query, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:organization:create')")
    @Operation(summary = "Create an organization")
    public RestResult.None create(@Validated({Default.class, Create.class}) OrgDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:organization:modify')")
    @Operation(summary = "Modify organization")
    public RestResult.None modify(@Validated({Default.class, Modify.class}) OrgDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:organization:remove')")
    @Operation(summary = "Remove organization")
    public RestResult.None remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
