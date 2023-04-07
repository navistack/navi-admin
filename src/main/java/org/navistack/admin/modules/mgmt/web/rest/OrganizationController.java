package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.navistack.admin.modules.common.query.OrganizationQuery;
import org.navistack.admin.modules.mgmt.service.OrganizationService;
import org.navistack.admin.modules.mgmt.service.dto.OrganizationDto;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
@Tag(name = "Organization Management")
@SecurityRequirement(name = "bearer-key")
public class OrganizationController {
    private final OrganizationService service;

    public OrganizationController(OrganizationService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:organization:query')")
    @Operation(summary = "Query Paged list of organizations")
    public Page<OrganizationDto> paginate(OrganizationQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:organization:create')")
    @Operation(summary = "Create organization")
    public void create(@Validated({Default.class, Create.class}) OrganizationDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:organization:modify')")
    @Operation(summary = "Modify organization")
    public void modify(@Validated({Default.class, Modify.class}) OrganizationDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:organization:remove')")
    @Operation(summary = "Remove organization")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}
