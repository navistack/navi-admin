package org.navistack.admin.modules.mgmt.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

@Data
public class DictItemDto {
    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 36)
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9$_]{1,48}$", message = "{validation.constraints.DictItem.itKey.message}")
    private String itKey;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String itValue;

    @Size(max = 140)
    private String remarks;

    @NotEmpty
    private String dictCode;
}
