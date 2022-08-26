package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.mgmt.service.PrivilegeService;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeDto;
import org.navistack.admin.modules.mgmt.service.dto.PrivilegeQueryParams;
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
@RequestMapping("/privilege")
@Tag(name = "Privilege Management")
public class PrivilegeController {
    private final PrivilegeService service;

    public PrivilegeController(PrivilegeService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:privilege:paginate')")
    @Operation(
            summary = "Query Paged list of privileges",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Privilege Management")
    public RestResult.Ok<Page<PrivilegeDto>> paginate(PrivilegeQueryParams queryParams, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(queryParams, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:privilege:create')")
    @Operation(
            summary = "Create a privilege",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Privilege Management")
    public RestResult.Ok<Void> create(@Validated({Default.class, Create.class}) PrivilegeDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:privilege:modify')")
    @Operation(
            summary = "Modify privilege",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Privilege Management")
    public RestResult.Ok<Void> modify(@Validated({Default.class, Modify.class}) PrivilegeDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:privilege:remove')")
    @Operation(
            summary = "Remove privilege",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Privilege Management")
    public RestResult.Ok<Void> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
