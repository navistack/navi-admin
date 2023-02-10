package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.mgmt.service.DictionaryService;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryDto;
import org.navistack.admin.modules.mgmt.service.dto.DictionaryItemDto;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary")
@Tag(name = "Dictionary Management")
@SecurityRequirement(name = "bearer-key")
public class DictionaryController {
    private final DictionaryService service;

    public DictionaryController(DictionaryService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:dictionary:query')")
    @Operation(summary = "Query Paged list of dictionaries")
    public RestResult<Page<DictionaryDto>, ?> paginate(DictionaryQuery query, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(query, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:dictionary:create')")
    @Operation(summary = "Create dictionary")
    public RestResult<Void, ?> create(@Validated({Default.class, Create.class}) DictionaryDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:dictionary:modify')")
    @Operation(summary = "Modify dictionary")
    public RestResult<Void, ?> modify(@Validated({Default.class, Modify.class}) DictionaryDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dictionary:remove')")
    @Operation(summary = "Remove dictionary")
    public RestResult<Void, ?> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }

    @GetMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:queryitem')")
    @Operation(summary = "Query Paged list of dictionary items")
    public RestResult<Page<DictionaryItemDto>, ?> paginateItem(DictionaryItemQuery query, PageRequest pageRequest) {
        return RestResult.ok(service.paginateItem(query, pageRequest));
    }

    @PostMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:createitem')")
    @Operation(summary = "Create dictionary item")
    public RestResult<Void, ?> createItem(@Validated({Default.class, Create.class}) DictionaryItemDto dto) {
        service.createItem(dto);
        return RestResult.ok();
    }

    @PatchMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:modifyitem')")
    @Operation(summary = "Modify dictionary item")
    public RestResult<Void, ?> modifyItem(@Validated({Default.class, Modify.class}) DictionaryItemDto dto) {
        service.modifyItem(dto);
        return RestResult.ok();
    }

    @DeleteMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:removeitem')")
    @Operation(summary = "Remove dictionary item")
    public RestResult<Void, ?> removeItem(@RequestParam Long id) {
        service.removeItem(id);
        return RestResult.ok();
    }
}
