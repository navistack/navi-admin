package org.navistack.admin.modules.mgmt.service.dto;

import lombok.Data;
import org.navistack.framework.mybatisplusplus.validation.groups.Create;
import org.navistack.framework.mybatisplusplus.validation.groups.Modify;

import javax.validation.constraints.*;

@Data
public class RegionDto {
    protected static final String CODE_PATTERN = "^[A-Za-z0-9-]{1,48}$";
    protected static final String CODE_VALIDATION_MESSAGE = "{validation.constraints.Region.code.message}";

    @Null(groups = Create.class)
    @NotNull(groups = Modify.class)
    private Long id;

    @NotEmpty
    @Pattern(regexp = CODE_PATTERN, message = CODE_VALIDATION_MESSAGE)
    private String code;

    @NotEmpty
    @Size(min = 1, max = 80)
    private String name;

    @NotEmpty
    @Pattern(regexp = CODE_PATTERN, message = CODE_VALIDATION_MESSAGE)
    private String parentCode;

    @Size(max = 140)
    private String remarks;
}
