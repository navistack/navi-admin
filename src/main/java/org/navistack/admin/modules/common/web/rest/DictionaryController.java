package org.navistack.admin.modules.common.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.query.DictionaryItemQuery;
import org.navistack.admin.modules.common.query.DictionaryQuery;
import org.navistack.admin.modules.common.service.DictionaryService;
import org.navistack.admin.modules.common.service.dto.DictionaryCreateDto;
import org.navistack.admin.modules.common.service.dto.DictionaryItemModifyDto;
import org.navistack.admin.modules.common.service.dto.DictionaryModifyDto;
import org.navistack.admin.modules.common.service.vm.DictionaryVm;
import org.navistack.admin.modules.system.web.rest.vm.DictionaryItemVm;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Page<DictionaryVm> paginate(DictionaryQuery query, PageRequest pageRequest) {
        return service.paginate(query, pageRequest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:dictionary:create')")
    @Operation(summary = "Create dictionary")
    public void create(@Validated DictionaryCreateDto dto) {
        service.create(dto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:dictionary:modify')")
    @Operation(summary = "Modify dictionary")
    public void modify(@Validated DictionaryModifyDto dto) {
        service.modify(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dictionary:remove')")
    @Operation(summary = "Remove dictionary")
    public void remove(@RequestParam Long id) {
        service.remove(id);
    }

    @GetMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:queryitem')")
    @Operation(summary = "Query Paged list of dictionary items")
    public Page<DictionaryItemVm> paginateItem(DictionaryItemQuery query, PageRequest pageRequest) {
        return service.paginateItem(query, pageRequest);
    }

    @PostMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:createitem')")
    @Operation(summary = "Create dictionary item")
    public void createItem(@Validated DictionaryItemModifyDto dto) {
        service.createItem(dto);
    }

    @PatchMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:modifyitem')")
    @Operation(summary = "Modify dictionary item")
    public void modifyItem(@Validated DictionaryItemModifyDto dto) {
        service.modifyItem(dto);
    }

    @DeleteMapping("/item")
    @PreAuthorize("hasAuthority('sys:dictionary:removeitem')")
    @Operation(summary = "Remove dictionary item")
    public void removeItem(@RequestParam Long id) {
        service.removeItem(id);
    }
}
