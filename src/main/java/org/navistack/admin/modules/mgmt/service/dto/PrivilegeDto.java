package org.navistack.admin.modules.mgmt.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

@Data
public class PrivilegeDto {
    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9:]{1,48}$", message = "{validation.constraints.Privilege.code.message}")
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    private Long parentId;

    @Size(max = 140)
    private String remarks;
}
