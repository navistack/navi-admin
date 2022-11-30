package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.query.DictItemQuery;
import org.navistack.admin.modules.mgmt.service.DictItemService;
import org.navistack.admin.modules.mgmt.service.dto.DictItemDto;
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
@RequestMapping("/dict-item")
@Tag(name = "Dictionary Item Management")
@SecurityRequirement(name = "bearer-key")
public class DictItemController {
    private final DictItemService service;

    public DictItemController(DictItemService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:dict_item:query')")
    @Operation(summary = "Query Paged list of dictionary items")
    public RestResult.Ok<Page<DictItemDto>> paginate(DictItemQuery query, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(query, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:dict_item:create')")
    @Operation(summary = "Create a dictionary item")
    public RestResult.None create(@Validated({Default.class, Create.class}) DictItemDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:dict_item:modify')")
    @Operation(summary = "Modify dictionary item")
    public RestResult.None modify(@Validated({Default.class, Modify.class}) DictItemDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dict_item:remove')")
    @Operation(summary = "Remove dictionary item")
    public RestResult.None remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
