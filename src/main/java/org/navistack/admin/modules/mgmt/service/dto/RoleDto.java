package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class RoleDto {
    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9$_]{1,48}$", message = "{validation.constraints.Role.code.message}")
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    @Size(max = 140)
    private String remarks;

    private List<Long> privilegeIds;
}
