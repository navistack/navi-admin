package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

import javax.validation.constraints.*;

@Data
public class DictDto {
    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9$_]{1,48}$", message = "{validation.constraints.Dict.code.message}")
    private String code;

    @Size(max = 80)
    private String name;

    @Size(max = 140)
    private String remarks;
}
