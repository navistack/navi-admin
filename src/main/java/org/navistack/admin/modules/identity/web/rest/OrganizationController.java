package org.navistack.admin.modules.identity.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.identity.query.OrganizationQuery;
import org.navistack.admin.modules.identity.service.OrganizationService;
import org.navistack.admin.modules.identity.service.dto.OrganizationCreateDto;
import org.navistack.admin.modules.identity.service.dto.OrganizationModifyDto;
import org.navistack.admin.modules.identity.service.vm.OrganizationVm;
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
    public Page<OrganizationVm> paginate(OrganizationQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:organization:create')")
    @Operation(summary = "Create organization")
    public void create(@Validated OrganizationCreateDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:organization:modify')")
    @Operation(summary = "Modify organization")
    public void modify(@Validated OrganizationModifyDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:organization:remove')")
    @Operation(summary = "Remove organization")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}
