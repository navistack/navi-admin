package org.navistack.admin.modules.identity.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.identity.query.RoleQuery;
import org.navistack.admin.modules.identity.service.RoleService;
import org.navistack.admin.modules.identity.service.dto.RoleCreateDto;
import org.navistack.admin.modules.identity.service.dto.RoleModifyDto;
import org.navistack.admin.modules.identity.service.vm.RoleDetailVm;
import org.navistack.admin.modules.identity.service.vm.RoleVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Tag(name = "Role Management")
@SecurityRequirement(name = "bearer-key")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:role:query')")
    @Operation(summary = "Query Paged list of roles")
    public Page<RoleVm> paginate(RoleQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:querydetail')")
    @Operation(summary = "Query details about role")
    public RoleDetailVm detail(@PathVariable Long id) {
        return service.queryDetailById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:create')")
    @Operation(summary = "Create a role")
    public void create(@Validated RoleCreateDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:role:modify')")
    @Operation(summary = "Modify role")
    public void modify(@Validated RoleModifyDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:role:remove')")
    @Operation(summary = "Remove role")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}
