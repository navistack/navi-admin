package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.mgmt.service.DictService;
import org.navistack.admin.modules.mgmt.service.dto.DictDto;
import org.navistack.admin.modules.mgmt.service.dto.DictQueryParams;
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
@RequestMapping("/dict")
@Tag(name = "Dictionary Management")
public class DictController {
    private final DictService service;

    public DictController(DictService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:dict:paginate')")
    @Operation(
            summary = "Query Paged list of dictionaries",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Management")
    public RestResult.Ok<Page<DictDto>> paginate(DictQueryParams queryParams, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(queryParams, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:dict:create')")
    @Operation(
            summary = "Create a dictionary",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Management")
    public RestResult.None create(@Validated({Default.class, Create.class}) DictDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:dict:modify')")
    @Operation(
            summary = "Modify dictionary",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Management")
    public RestResult.None modify(@Validated({Default.class, Modify.class}) DictDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dict:remove')")
    @Operation(
            summary = "Remove dictionary",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Dictionary Management")
    public RestResult.None remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
