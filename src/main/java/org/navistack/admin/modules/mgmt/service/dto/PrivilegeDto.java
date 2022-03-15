package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.framework.mybatisplusplus.validation.groups.Create;
import org.navistack.framework.mybatisplusplus.validation.groups.Modify;

import javax.validation.constraints.*;

@Data
public class PrivilegeDto {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9:]+$";

    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 48)
    @Pattern(regexp = CODE_PATTERN, message = "{Pattern.Privilege.code}")
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    private Long parentId;

    @Size(max = 140)
    private String description;
}
