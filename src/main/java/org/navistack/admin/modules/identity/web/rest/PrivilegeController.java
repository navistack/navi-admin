package org.navistack.admin.modules.identity.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.navistack.admin.modules.identity.query.PrivilegeQuery;
import org.navistack.admin.modules.identity.service.PrivilegeService;
import org.navistack.admin.modules.identity.service.dto.PrivilegeDto;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privilege")
@Tag(name = "Privilege Management")
@SecurityRequirement(name = "bearer-key")
public class PrivilegeController {
    private final PrivilegeService service;

    public PrivilegeController(PrivilegeService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:privilege:query')")
    @Operation(summary = "Query Paged list of privileges")
    public Page<PrivilegeDto> paginate(PrivilegeQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:privilege:create')")
    @Operation(summary = "Create a privilege")
    public void create(@Validated({Default.class, Create.class}) PrivilegeDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:privilege:modify')")
    @Operation(summary = "Modify privilege")
    public void modify(@Validated({Default.class, Modify.class}) PrivilegeDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:privilege:remove')")
    @Operation(summary = "Remove privilege")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}