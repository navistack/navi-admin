package org.navistack.admin.modules.identity.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.identity.query.UserQuery;
import org.navistack.admin.modules.identity.service.UserService;
import org.navistack.admin.modules.identity.service.dto.UserCreateDto;
import org.navistack.admin.modules.identity.service.dto.UserModifyDto;
import org.navistack.admin.modules.identity.service.vm.UserDetailVm;
import org.navistack.admin.modules.identity.service.vm.UserVm;
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
    public Page<UserVm> paginate(UserQuery query, PageRequest pageRequest) {
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
    public void create(@Validated UserCreateDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:user:modify')")
    @Operation(summary = "Modify user")
    public void modify(@Validated UserModifyDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:user:remove')")
    @Operation(summary = "Remove user")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }
}
