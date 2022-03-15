package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.mgmt.service.DictItemService;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
import org.navistack.admin.modules.mgmt.service.dto.DictItemQueryParams;
import org.navistack.framework.mybatisplusplus.validation.groups.Create;
import org.navistack.framework.mybatisplusplus.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/dict-item")
@Tag(name = "Dictionary Item Management")
public class DictItemController {
    private final DictItemService service;

    public DictItemController(DictItemService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:dict_item:paginate')")
    @Operation(
            summary = "Query Paged list of dictionary items",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Item Management")
    public RestResult<Page<DictItemDto>, ?> paginate(DictItemQueryParams params, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(params, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:dict_item:create')")
    @Operation(
            summary = "Create a dictionary item",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Item Management")
    public RestResult<Void, ?> create(@Validated({Default.class, Create.class}) DictItemDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:dict_item:modify')")
    @Operation(
            summary = "Modify dictionary item",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Item Management")
    public RestResult<Void, ?> modify(@Validated({Default.class, Modify.class}) DictItemDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dict_item:remove')")
    @Operation(
            summary = "Remove dictionary item",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Item Management")
    public RestResult<Void, ?> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
