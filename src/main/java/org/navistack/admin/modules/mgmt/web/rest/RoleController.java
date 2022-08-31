package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.mgmt.service.RoleService;
import org.navistack.admin.modules.mgmt.service.dto.RoleDto;
import org.navistack.admin.modules.mgmt.service.dto.RoleQueryDto;
import org.navistack.admin.modules.mgmt.service.vm.RoleDetailVm;
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
@RequestMapping("/role")
@Tag(name = "Role Management")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:role:query')")
    @Operation(
            summary = "Query Paged list of roles",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Role Management")
    public RestResult.Ok<Page<RoleDto>> paginate(RoleQueryDto queryDto, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(queryDto, pageRequest));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:querydetail')")
    @Operation(
            summary = "Query details about role",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Role Management")
    public RestResult.Ok<RoleDetailVm> detail(@PathVariable Long id) {
        return RestResult.ok(service.queryDetailById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:create')")
    @Operation(
            summary = "Create a role",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Role Management")
    public RestResult.None create(@Validated({Default.class, Create.class}) RoleDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:role:modify')")
    @Operation(
            summary = "Modify role",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Role Management")
    public RestResult.None modify(@Validated({Default.class, Modify.class}) RoleDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:role:remove')")
    @Operation(
            summary = "Remove role",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Role Management")
    public RestResult.None remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
