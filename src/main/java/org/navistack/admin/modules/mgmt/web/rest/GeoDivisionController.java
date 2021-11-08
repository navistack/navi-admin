package org.navistack.admin.modules.mgmt.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.common.entity.GeoDivision;
import org.navistack.admin.modules.mgmt.service.GeoDivisionService;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionDto;
import org.navistack.admin.modules.mgmt.service.dto.GeoDivisionQueryParams;
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
@RequestMapping("/geo-division")
@Tag(name = "Geographical Division Management")
public class GeoDivisionController {
    private final GeoDivisionService service;

    public GeoDivisionController(GeoDivisionService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:geo_division:paginate')")
    @Operation(
            summary = "Query Paged list of geographical divisions",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Geographical Division Management")
    public RestResult<Page<GeoDivision>, ?> paginate(GeoDivisionQueryParams params, PageRequest pageRequest) {
        return RestResult.ok(service.paginate(params, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:geo_division:create')")
    @Operation(
            summary = "Create a geographical division",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Geographical Division Management")
    public RestResult<Void, ?> create(@Validated({Default.class, Create.class}) GeoDivisionDto dto) {
        service.create(dto);
        return RestResult.ok();
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('sys:geo_division:modify')")
    @Operation(
            summary = "Modify geographical division",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Geographical Division Management")
    public RestResult<Void, ?> modify(@Validated({Default.class, Modify.class}) GeoDivisionDto dto) {
        service.modify(dto);
        return RestResult.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:geo_division:remove')")
    @Operation(
            summary = "Remove geographical division",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @Tag(name = "Geographical Division Management")
    public RestResult<Void, ?> remove(@RequestParam Long id) {
        service.remove(id);
        return RestResult.ok();
    }
}
