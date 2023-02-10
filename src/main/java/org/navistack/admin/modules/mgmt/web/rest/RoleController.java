package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.navistack.admin.modules.common.query.RoleQuery;
import org.navistack.admin.modules.mgmt.service.RoleService;
import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.web.rest.RestResult;
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
    public RestResult<Page<RoleDto>, ?> paginate(RoleQuery query, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(query, pageRequest));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:querydetail')")
    @Operation(summary = "Query details about role")
    public RestResult<RoleDetailVm, ?> detail(@PathVariable Long id) {
        return RestResult.ok(service.queryDetailById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:create')")
    @Operation(summary = "Create a role")
    public RestResult<Void, ?> create(@Validated({Default.class, Create.class}) RoleDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:role:modify')")
    @Operation(summary = "Modify role")
    public RestResult<Void, ?> modify(@Validated({Default.class, Modify.class}) RoleDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:role:remove')")
    @Operation(summary = "Remove role")
    public RestResult<Void, ?> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
