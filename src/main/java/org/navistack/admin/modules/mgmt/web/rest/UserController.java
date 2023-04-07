package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.navistack.admin.modules.common.query.UserQuery;
import org.navistack.admin.modules.mgmt.service.UserService;
import org.navistack.admin.modules.mgmt.service.dto.UserDto;
import org.navistack.admin.modules.mgmt.service.vm.UserDetailVm;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Management")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:query')")
    @Operation(summary = "Query Paged list of users")
    public Page<UserDto> paginate(UserQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:querydetail')")
    @Operation(summary = "Query details about user")
    public UserDetailVm detail(@PathVariable Long id) {
        return service.queryDetailById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:create')")
    @Operation(summary = "Create a user")
    public void create(@Validated({Default.class, Create.class}) UserDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:user:modify')")
    @Operation(summary = "Modify user")
    public void modify(@Validated({Default.class, Modify.class}) UserDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:user:remove')")
    @Operation(summary = "Remove user")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}
