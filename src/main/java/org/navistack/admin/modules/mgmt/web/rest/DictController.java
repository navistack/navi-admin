package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.query.DictItemQuery;
import org.navistack.admin.modules.common.query.DictQuery;
import org.navistack.admin.modules.mgmt.service.DictService;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
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
@RequestMapping("/dict")
@Tag(name = "Dictionary Management")
@SecurityRequirement(name = "bearer-key")
public class DictController {
    private final DictService service;

    public DictController(DictService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:dict:query')")
    @Operation(summary = "Query Paged list of dictionaries")
    public RestResult.Ok<Page<DictDto>> paginate(DictQuery query, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(query, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:dict:create')")
    @Operation(summary = "Create a dictionary")
    public RestResult.None create(@Validated({Default.class, Create.class}) DictDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:dict:modify')")
    @Operation(summary = "Modify dictionary")
    public RestResult.None modify(@Validated({Default.class, Modify.class}) DictDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dict:remove')")
    @Operation(summary = "Remove dictionary")
    public RestResult.None remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }

    @GetMapping("/item")
    @PreAuthorize("hasAuthority('sys:dict:queryitem')")
    @Operation(summary = "Query Paged list of dictionary items")
    public RestResult.Ok<Page<DictItemDto>> paginateItem(DictItemQuery query, PageRequest pageRequest) {
        return RestResult.ok(service.paginateItem(query, pageRequest));
    }

    @PostMapping("/item")
    @PreAuthorize("hasAuthority('sys:dict:createitem')")
    @Operation(summary = "Create a dictionary item")
    public RestResult.None createItem(@Validated({Default.class, Create.class}) DictItemDto dto) {
        service.createItem(dto);
        return RestResult.ok();
    }

    @PatchMapping("/item")
    @PreAuthorize("hasAuthority('sys:dict:modifyitem')")
    @Operation(summary = "Modify dictionary item")
    public RestResult.None modifyItem(@Validated({Default.class, Modify.class}) DictItemDto dto) {
        service.modifyItem(dto);
        return RestResult.ok();
    }

    @DeleteMapping("/item")
    @PreAuthorize("hasAuthority('sys:dict:removeitem')")
    @Operation(summary = "Remove dictionary item")
    public RestResult.None removeItem(@RequestParam Long id) {
        service.removeItem(id);
        return RestResult.ok();
    }
}
