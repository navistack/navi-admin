package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.admin.support.validation.groups.Create;
import org.navistack.admin.support.validation.groups.Modify;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class RoleDto {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9$_]+$";

    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @Size(min = 1, max = 48)
    @Pattern(regexp = CODE_PATTERN, message = "{Pattern.Role.code}")
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    @Size(max = 140)
    private String description;

    private List<Long> privilegeIds;
}
