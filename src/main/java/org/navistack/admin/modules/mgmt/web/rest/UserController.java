package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.mgmt.service.UserService;
import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.dto.UserQueryParams;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/user")
@Tag(name = "User Management")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:paginate')")
    @Operation(
            summary = "Query Paged list of users",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "User Management")
    public RestResult<Page<User>, ?> paginate(UserQueryParams queryParams, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(queryParams, pageRequest));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:detail')")
    @Operation(
            summary = "Query details about user",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "User Management")
    public RestResult<UserDetailVm, ?> detail(@PathVariable Long id) {
        return RestResult.ok(service.queryDetailById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:create')")
    @Operation(
            summary = "Create a user",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "User Management")
    public RestResult<Void, ?> create(@Validated({Default.class, Create.class}) UserDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:user:modify')")
    @Operation(
            summary = "Modify user",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "User Management")
    public RestResult<Void, ?> modify(@Validated({Default.class, Modify.class}) UserDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:user:remove')")
    @Operation(
            summary = "Remove user",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "User Management")
    public RestResult<Void, ?> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
